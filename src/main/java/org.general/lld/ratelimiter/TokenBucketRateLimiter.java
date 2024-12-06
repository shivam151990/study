package lld.ratelimiter;

import java.util.concurrent.atomic.AtomicInteger;

public class TokenBucketRateLimiter {
    private final int maxTokens;
    private final int refillWindow;
    private final AtomicInteger tokens;
    private long lastRefillTs;

    public TokenBucketRateLimiter(int maxTokens) {
        this.refillWindow = 1000;
        this.maxTokens = maxTokens;
        this.tokens = new AtomicInteger(maxTokens);
        lastRefillTs = System.currentTimeMillis();
    }

    /**
     * - Initialize the class with maxTokens
     * - Request comes to allow or disallow
     *      - refill bucket based on current and lastTime and update lastRefillTime
     *      - check if the request can be allowed based on the current token count
     *          - If can be allowed then decrement current token count
     *          - else return false
     */

    public boolean tryAcquire() {
        refillBucket();
        if (tokens.get() > 0) {
            tokens.decrementAndGet();
            return true;
        }
        return false;
    }

    private void refillBucket() {
        long curTime = System.currentTimeMillis();
        long timeSinceLastRefill = curTime - lastRefillTs;
        int tokensToAdd = (int) (timeSinceLastRefill / refillWindow) * maxTokens;

        if (tokensToAdd > 0) {
            int newTokenCount = Math.min(tokens.get() + tokensToAdd, maxTokens);
            tokens.set(newTokenCount);
            lastRefillTs = curTime;
        }
    }
}
