"You are building a library that an API gateway uses to decide whether a request from a user should be allowed. 
Each user may make at most N requests, and the limit must hold even when many threads call it at once."

Example
Limit is 5 requests, refilling at 1 token per second. User u1 calls allow("u1") 5 times quickly 
and gets true five times. The 6th call returns false. After 2 seconds, two more calls return true.

Requirements

1. allow(userId) returns true or false.
2. Use the token bucket: a bucket of N tokens, 
   each request takes one, and tokens refill lazily from timestamps with no background thread.
3. Safe under concurrency: one lock per user, or a ConcurrentHashMap<String, Bucket> with a synchronized allow on each bucket.
4. A test with 10 threads making 1,000 calls in total that shows the number of allowed calls is exactly right.