package multithreading.ratelimiting;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class TokenBucket {

    private long lastRequestTime;
    private final int MAX_TOKENS;
    private int currentTokens;

    public TokenBucket(int MAX_TOKENS) {
        this.MAX_TOKENS = MAX_TOKENS;
        this.currentTokens = 0;
        this.lastRequestTime = System.currentTimeMillis();
    }

    @SneakyThrows
    public synchronized void getTokens() {
        currentTokens += (System.currentTimeMillis() - lastRequestTime) / 1000;

        if (currentTokens > MAX_TOKENS) {
            currentTokens = MAX_TOKENS;
        }

        if (currentTokens == 0) {
            System.out.println("WAITING, NO TOKENS.....");
            TimeUnit.SECONDS.sleep(1);
        } else {
            currentTokens--;
        }

        lastRequestTime = System.currentTimeMillis();
        System.out.println("GRANTING TOKEN!!!");
    }

}
