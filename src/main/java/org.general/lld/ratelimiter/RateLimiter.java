package lld.ratelimiter;

public interface RateLimiter {
    boolean tryAcquire(String userId);
}
