package com.shivam151990.multithreading.ratelimiting.token;

public interface RateLimiter {
    boolean allow(String userId);
}

