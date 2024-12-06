package lld.ratelimiter;

public class FixedWindowGlobalRateLimiter {

    private final int maxWindow;
    private int windowSize;
    private long windowStart;
    private int requestCount;

    public FixedWindowGlobalRateLimiter(int maxWindow) {
        this.maxWindow = maxWindow;
        this.windowStart = System.currentTimeMillis();
    }

    public boolean tryAcquire() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - windowStart > maxWindow) {
            requestCount = 1;
            windowStart = currentTime;
            return true;
        } else {
            if (requestCount < maxWindow) {
                requestCount++;
                return true;
            } else {
                return false;
            }
        }
    }
}
