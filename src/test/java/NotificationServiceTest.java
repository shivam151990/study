import com.shivam151990.lld.notification_service.NotificationChannel;
import com.shivam151990.lld.notification_service.NotificationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NotificationServiceTest {

    private final PrintStream originalOut = System.out;
    private CapturingOut out;
    private NotificationService service;

    @BeforeEach
    public void before() {
        // Channels print to System.out, so capture it to see what was delivered
        out = new CapturingOut();
        System.setOut(out);
        service = new NotificationService();
    }

    @AfterEach
    public void after() {
        System.setOut(originalOut);
    }

    // ---- 1 & 3. delivery ----

    @Test
    public void sendsOnSinglePreferredChannel() {
        service.setPreferences("u1", List.of(NotificationChannel.SMS));
        service.send("u1", "hello");
        assertEquals(List.of("SMS: hello"), out.lines);
    }

    @Test
    public void sendsOnEveryPreferredChannel() {
        service.setPreferences("u1", List.of(NotificationChannel.EMAIL, NotificationChannel.SMS, NotificationChannel.PUSH));
        service.send("u1", "hello");
        assertEquals(List.of("EMAIL: hello", "SMS: hello", "PUSH: hello"), out.lines);
    }

    @Test
    public void userWithoutPreferencesGetsEmail() {
        service.send("u1", "hello");
        assertEquals(List.of("EMAIL: hello"), out.lines);
    }

    @Test
    public void userWithEmptyPreferencesGetsEmail() {
        service.setPreferences("u1", List.of());
        service.send("u1", "hello");
        assertEquals(List.of("EMAIL: hello"), out.lines);
    }

    // ---- 2. preferences ----

    @Test
    public void preferencesArePerUser() {
        service.setPreferences("u1", List.of(NotificationChannel.SMS));
        service.setPreferences("u2", List.of(NotificationChannel.PUSH));
        service.send("u1", "a");
        service.send("u2", "b");
        assertEquals(List.of("SMS: a", "PUSH: b"), out.lines);
    }

    @Test
    public void setPreferencesReplacesPrevious() {
        service.setPreferences("u1", List.of(NotificationChannel.SMS));
        service.setPreferences("u1", List.of(NotificationChannel.PUSH));
        service.send("u1", "hello");
        assertEquals(List.of("PUSH: hello"), out.lines);
    }

    // ---- 4. failures ----

    @Test
    public void failingChannelDoesNotStopOthers() {
        out.failOnPrefix = "SMS";
        service.setPreferences("u1", List.of(NotificationChannel.EMAIL, NotificationChannel.SMS, NotificationChannel.PUSH));
        service.send("u1", "hello");
        assertEquals(List.of("EMAIL: hello", "PUSH: hello"), out.lines);
    }

    @Test
    public void failuresAreRecorded() {
        out.failOnPrefix = "SMS";
        service.setPreferences("u1", List.of(NotificationChannel.SMS, NotificationChannel.PUSH));
        service.send("u1", "hello");
        assertEquals(1, service.getFailures().size());
        assertTrue(service.getFailures().get(0).contains("SMS"));
        assertTrue(service.getFailures().get(0).contains("u1"));
    }

    @Test
    public void noFailuresWhenAllChannelsSucceed() {
        service.setPreferences("u1", List.of(NotificationChannel.EMAIL, NotificationChannel.PUSH));
        service.send("u1", "hello");
        assertTrue(service.getFailures().isEmpty());
    }

    /** Captures printed lines; can simulate a channel outage by throwing for lines with a given prefix. */
    private static class CapturingOut extends PrintStream {
        private final List<String> lines = new ArrayList<>();
        private String failOnPrefix;

        CapturingOut() {
            super(new ByteArrayOutputStream());
        }

        @Override
        public void println(String line) {
            if (failOnPrefix != null && line.startsWith(failOnPrefix)) {
                throw new RuntimeException(failOnPrefix + " gateway down");
            }
            lines.add(line);
        }
    }
}
