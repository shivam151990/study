package lld.ratelimiter;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class SlidingWindowRateLimiter implements RateLimiter {

    private final int maxWindowLength;
    private final int maxWindowSizeInSec;
    private final Map<String, Queue<LocalTime>> reqTs;

    public SlidingWindowRateLimiter(int maxWindowLength, int maxWindowSizeInSec) {
        this.maxWindowLength = maxWindowLength;
        this.maxWindowSizeInSec = maxWindowSizeInSec;
        reqTs = new HashMap<>();
    }

    @Override
    public boolean tryAcquire(String userId) {
        LocalTime currentTime = LocalTime.now();
        reqTs.putIfAbsent(userId, new LinkedList<>());

        Queue<LocalTime> qu = reqTs.get(userId);
        while (!qu.isEmpty() && isOutOfWindow(qu.peek(), currentTime)) {
            qu.poll();
        }
        if (qu.size() < maxWindowLength) {
            qu.offer(currentTime);
            return true;
        }
        return false;
    }

    private boolean isOutOfWindow(LocalTime oldRequest, LocalTime currentTime) {
        Duration diff = Duration.between(oldRequest, currentTime);
        return diff.getSeconds() >= maxWindowSizeInSec;
    }
}
