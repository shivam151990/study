package lld.ratelimiter;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class FixedWindowRateLimiter implements RateLimiter {

    private int maxWindowLength;
    private int maxTimeWindow;
    private Map<String, Integer> requestCount;
    private Map<String, LocalTime> winStartTime;


    public FixedWindowRateLimiter(int maxWindowLength, int maxTimeWindow) {
        this.maxWindowLength = maxWindowLength;
        this.maxTimeWindow = maxTimeWindow;
        requestCount = new HashMap<>();
        winStartTime = new HashMap<>();
    }

    @Override
    public boolean tryAcquire(String userId) {
        LocalTime currentTime = LocalTime.now();
        requestCount.putIfAbsent(userId, 0);
        winStartTime.putIfAbsent(userId, currentTime);

        LocalTime windowStartTime = winStartTime.get(userId);
        Duration timeDiff = Duration.between(currentTime, windowStartTime);
        if (timeDiff.getSeconds() >= maxTimeWindow) {
            requestCount.put(userId, 0);
            winStartTime.put(userId, currentTime);
        }
        if (requestCount.get(userId) < maxWindowLength) {
            requestCount.put(userId, requestCount.get(userId) + 1);
            return true;
        }
        return false;
    }


}
