package com.shivam151990.multithreading.ratelimiting.token;

import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketRateLimiter implements RateLimiter {

    private int maxTokens;
    private ConcurrentHashMap<String, Bucket> bucket;

    public TokenBucketRateLimiter(int maxTokens) {
        this.maxTokens = maxTokens;
        this.bucket = new ConcurrentHashMap<>();
    }


    @Override
    public boolean allow(String userId) {
        Bucket cur = bucket.computeIfAbsent(userId, b -> new Bucket(maxTokens, System.nanoTime()));
        synchronized (cur) {
            refillBucket(cur);
            if (cur.getTokens() >= 1.0) {
                cur.setTokens(cur.getTokens() - 1.0);
                return true;
            }
            return false;
        }
    }

    private void refillBucket(Bucket cur) {
        long now = System.nanoTime();
        double newTokens = (now - cur.getLastRefillNano()) / 1_000_000_000.0;
        cur.setTokens(Math.min(cur.getTokens() + newTokens, maxTokens));
        cur.setLastRefillNano(now);
    }
}
