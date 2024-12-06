package lld.ratelimiter;

import java.util.LinkedList;
import java.util.Queue;

public class SlidingWindowGlobalRateLimiter {

    private long maxWindow;
    private int maxRequests;
    private Queue<Long> qu;

    public SlidingWindowGlobalRateLimiter(int maxWindow, int maxRequests) {
        this.maxWindow = maxWindow;
        this.maxRequests = maxRequests;
        this.qu = new LinkedList<>();
    }

    public boolean tryAcquire() {
        long curTs = System.currentTimeMillis();
        while (!qu.isEmpty() && curTs - qu.peek() > maxWindow) {
            qu.poll();
        }
        if (qu.size() > maxRequests) {
            qu.offer(curTs);
            return true;
        } else {
            return false;
        }
    }
}
