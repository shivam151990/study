import com.shivam151990.lld.stock_observer.service.StockManager;
import com.shivam151990.lld.stock_observer.exception.StockNotAvailabeException;
import com.shivam151990.lld.stock_observer.service.StockObserver;
import com.shivam151990.lld.stock_observer.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StockObserverTest {

    private StockManager manager;
    private RecordingObserver observer;

    @BeforeEach
    public void before() {
        manager = new StockManager();
        observer = new RecordingObserver();
        manager.add(observer);
        manager.setThreshold("p1", 5);
    }

    // ---- 1 & 5. add / remove / getStock ----

    @Test
    public void addAndGetStock() {
        manager.addStock("p1", 10);
        manager.addStock("p1", 5);
        assertEquals(15, manager.getStock("p1"));
    }

    @Test
    public void getStockForUnknownProductIsZero() {
        assertEquals(0, manager.getStock("unknown"));
    }

    @Test
    public void removeStockReducesStock() {
        manager.addStock("p1", 10);
        manager.removeStock("p1", 3);
        assertEquals(7, manager.getStock("p1"));
    }

    @Test
    public void removeAllStockIsAllowed() {
        manager.addStock("p1", 10);
        assertDoesNotThrow(() -> manager.removeStock("p1", 10));
        assertEquals(0, manager.getStock("p1"));
    }

    @Test
    public void removeMoreThanAvailableFails() {
        manager.addStock("p1", 10);
        assertThrows(StockNotAvailabeException.class, () -> manager.removeStock("p1", 11));
        assertEquals(10, manager.getStock("p1"));
    }

    @Test
    public void removeUnknownProductFails() {
        assertThrows(StockNotAvailabeException.class, () -> manager.removeStock("unknown", 1));
    }

    // ---- 2 & 3. threshold alert ----

    @Test
    public void addingStockDoesNotNotify() {
        manager.addStock("p1", 10);
        assertTrue(observer.messages.isEmpty());
    }

    @Test
    public void notifiesWhenStockFallsBelowThreshold() {
        manager.addStock("p1", 10);
        manager.removeStock("p1", 6);  // 4 <= 5
        assertEquals(1, observer.messages.size());
    }

    @Test
    public void notifiesWhenStockFallsExactlyToThreshold() {
        manager.addStock("p1", 10);
        manager.removeStock("p1", 5);  // 5 <= 5
        assertEquals(1, observer.messages.size());
    }

    @Test
    public void noNotificationWhileAboveThreshold() {
        manager.addStock("p1", 10);
        manager.removeStock("p1", 4);  // 6 > 5
        assertTrue(observer.messages.isEmpty());
    }

    @Test
    public void notifiesOnlyOnceForLaterRemovals() {
        manager.addStock("p1", 10);
        manager.removeStock("p1", 5);
        manager.removeStock("p1", 2);
        manager.removeStock("p1", 1);
        assertEquals(1, observer.messages.size());
    }

    @Test
    public void allRegisteredListenersAreNotified() {
        RecordingObserver second = new RecordingObserver();
        manager.add(second);
        manager.add(new User("u1"));
        manager.addStock("p1", 10);
        manager.removeStock("p1", 6);
        assertEquals(1, observer.messages.size());
        assertEquals(1, second.messages.size());
    }

    @Test
    public void removedListenerIsNotNotified() {
        manager.remove(observer);
        manager.addStock("p1", 10);
        manager.removeStock("p1", 6);
        assertTrue(observer.messages.isEmpty());
    }

    @Test
    public void productWithoutThresholdNeverNotifies() {
        manager.addStock("p2", 10);
        manager.removeStock("p2", 10);
        assertTrue(observer.messages.isEmpty());
    }

    @Test
    public void thresholdsArePerProduct() {
        manager.setThreshold("p2", 2);
        manager.addStock("p1", 10);
        manager.addStock("p2", 10);
        manager.removeStock("p2", 6);  // p2 at 4, above its threshold of 2
        assertTrue(observer.messages.isEmpty());
        manager.removeStock("p1", 6);  // p1 at 4, below its threshold of 5
        assertEquals(1, observer.messages.size());
        assertTrue(observer.messages.get(0).contains("p1"));
    }

    // ---- 4. re-arm ----

    @Test
    public void alertReArmsWhenStockRisesAboveThreshold() {
        manager.addStock("p1", 10);
        manager.removeStock("p1", 6);  // alert 1
        manager.addStock("p1", 5);     // 9 > 5, re-armed
        manager.removeStock("p1", 5);  // alert 2
        assertEquals(2, observer.messages.size());
    }

    @Test
    public void risingOnlyToThresholdDoesNotReArm() {
        manager.addStock("p1", 10);
        manager.removeStock("p1", 6);  // 4, alert
        manager.addStock("p1", 1);     // 5, still not above threshold
        manager.removeStock("p1", 1);  // 4
        assertEquals(1, observer.messages.size());
    }

    /** Records every message it receives. */
    private static class RecordingObserver implements StockObserver {
        private final List<String> messages = new ArrayList<>();

        @Override
        public void receive(String msg) {
            messages.add(msg);
        }
    }
}
