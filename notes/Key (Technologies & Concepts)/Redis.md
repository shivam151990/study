#### Overview

**Redis (REmote DIctionary Server)** is an **open-source, in-memory data structure store**, used as a **database, cache, and message broker**. It is known for **high performance**, **low latency**, and support for rich data structures such as:
- Strings
- Lists
- Sets
- Hashes
- Sorted Sets
- Streams
- Bitmaps
- HyperLogLogs
- Geospatial indexes
Redis stores data **in memory** (RAM), making operations extremely fast, and can optionally persist it to disk for durability.

The core structure underneath Redis is a key-value store. Keys are strings while values which can be any of the data structures supported by Redis: binary data and strings, sets, lists, hashes, sorted sets, etc. All objects in Redis have a key.

**The choice of keys is important as these keys might be stored in separate nodes based on your [infrastructure configuration](https://www.hellointerview.com/learn/system-design/deep-dives/redis#infrastructure-configurations). Effectively, the way you organize the keys will be the way you organize your data and scale your Redis cluster.**

Redis' wire protocol is a custom query language comprised of simple strings which are used for all functionality of Redis. The CLI is really simple, you can literally connect to a Redis instance and run these commands from the CLI.

`SET foo 1   
`GET foo                                               # Returns 1 
`INCR foo									           # Returns 2 
`XADD mystream * name Sara surname OConnor             # Adds an item to a stream`


Rather than iterating over, sorting, and ordering rows, what if the data was in data structures you wanted from the ground up? Early on, it was used much like Memcached, but as Redis improved, it became viable for many other use cases, including publish-subscribe mechanisms, streaming, and queues.

![Redis data types for storage](https://substackcdn.com/image/fetch/w_1456,c_limit,f_auto,q_auto:good,fl_progressive:steep/https%3A%2F%2Fsubstack-post-media.s3.amazonaws.com%2Fpublic%2Fimages%2Fcb654322-3c8b-4b6a-ae3b-0e82a213d73d_1082x1436.jpeg "Redis data types for storage")

Redis data types for storage

Primarily, Redis is an in-memory database used as a cache in front of another "real" database like MySQL or PostgreSQL to help improve application performance. It leverages the speed of memory and alleviates load off the central application database for:

- Data that changes infrequently  and is requested often
- Data that is less mission-critical and is frequently evolving.

Examples of above data  can include session or data caches and leaderboard or roll-up analytics for dashboards.

![How redis is used for caching](https://substackcdn.com/image/fetch/w_1456,c_limit,f_auto,q_auto:good,fl_progressive:steep/https%3A%2F%2Fsubstack-post-media.s3.amazonaws.com%2Fpublic%2Fimages%2F8533bf77-bf19-4feb-b744-c4c29bd38592_1616x892.jpeg "How redis is used for caching")

However, for many use cases, Redis offers enough guarantees that it can be used as a full-fledged primary database. Coupled with Redis plug-ins and its various High Availability (HA) setups, Redis as a database  has become incredibly useful for certain scenarios and workloads.

Another important aspect is that Redis blurred the lines between a cache and datastore. Important note to understand here is that reading and manipulating data in memory is much faster than anything possible in traditional datastores using SSDs or HDDs.

Originally Redis was most commonly compared to Memcached, which lacked any nonvolatile persistence at the time.

> Memcached
> 
> Memcached was created by Brad Fitzpatrick in 2003, predating Redis by six years. It originally started as a Perl project and was later rewritten in C. It was the de facto caching tool of its day. The main differentiating point between it and Redis is its lack of data types and its limited eviction policy of just LRU (least recently used).
> 
> Another difference is that Redis is single-threaded while Memcached is multithreaded. Memcached might be performant in a strictly caching environment but requires some setup in a distributed cluster, while Redis has support for this out of the box.

Here is a current breakdown of capabilities between these two caches.  

![](https://substackcdn.com/image/fetch/w_1456,c_limit,f_auto,q_auto:good,fl_progressive:steep/https%3A%2F%2Fsubstack-post-media.s3.amazonaws.com%2Fpublic%2Fimages%2F8495985a-fc42-4ef2-9d61-dc598986a3a7_1608x1312.png)


Memcached Redis Sub-millisecond latency Yes Yes Developer ease of use Yes Yes Data partitioning Yes Yes Support for a broad set of programming languages Yes Yes Advanced data structures - Yes Multithreaded architecture Yes - Snapshots - Yes Replication - Yes Transactions - Yes Pub/Sub - Yes Lua scripting - Yes Geospatial support - Yes

Although now configurable in how it persists data to disk, when it was first introduced, Redis used snapshots where asynchronous copies of the data in memory were persisted to disk for long-term storage. Unfortunately, this mechanism has the downside of potentially losing your data between snapshots.
Redis has matured since its inception in 2009. We will cover most of its architecture and topologies so you can add Redis to your data storage system arsenal.

---

Redis is used in scenarios where **speed and performance are critical**. Common use cases include:

---
✅ 1. **Caching**
- To reduce database load and latency by storing frequently accessed data in memory.
- Example: Caching user sessions, product listings, or API responses.
---
✅ 2. **Session Storage**
- Store user sessions for web applications.
- Useful in distributed systems where session state should be shared across multiple servers.
---
✅ 3. **Rate Limiting / Throttling**
- Track user actions (e.g., login attempts, API calls) over time windows.
- Leverages Redis's atomic increment and expiration commands.
---
✅ 4. **Pub/Sub (Messaging Queue)**
- Redis supports **Publish/Subscribe** messaging.
- Useful for building real-time features like chat apps or live notifications.
---
✅ 5. **Leaderboards / Real-Time Analytics**
- Use **Sorted Sets** for ranking systems with scores.
- Example: Gaming leaderboards or trending content.
---
✅ 6. **Distributed Locks**
- Implements distributed locks using `SETNX` or Redlock algorithm.
- Ensures mutual exclusion in distributed applications.
---
✅ 7. **Background Job Queues**
- Used with tools like **Bull**, **Celery**, or **Sidekiq** to store background jobs.
- Provides task queues and delayed execution.
---
✅ 8. **Geospatial Data**
- Redis can store and query location-based data.
- Useful for apps with location search or mapping (e.g., "find nearest store").
---
##### When _Not_ to Use Redis
- **Large datasets that don't fit in memory**.
- **Complex relational queries** (better suited for SQL databases).
- **Data requiring strong durability or ACID compliance**.


#### Infrastructure Configurations

We will be focusing mainly on these configurations:
1. Single Redis Instance
2. Redis HA
3. Redis Sentinel
4. Redis Cluster

##### Single Redis Instance

![Simple Redis deployment.](https://substackcdn.com/image/fetch/w_1456,c_limit,f_auto,q_auto:good,fl_progressive:steep/https%3A%2F%2Fsubstack-post-media.s3.amazonaws.com%2Fpublic%2Fimages%2F2ea006d0-74cd-44f5-8eac-43e80d9a1b27_869x485.png "Simple Redis deployment.")

Simple Redis deployment.

Single Redis instance is the most straightforward deployment of Redis. It allows users to set up and run small instances that can help them grow and speed up their services. However, this deployment isn't without shortcomings. For example, if this instance fails or is unavailable, all client calls to Redis will fail and therefore degrade the system's overall performance and speed.

Given enough memory and server resources, this instance can be powerful. A scenario primarily used for caching could result in a significant performance boost with minimal setup. Given enough system resources, you could deploy this Redis service on the same box the application is running.

Understanding a few Redis concepts on managing data within the system is essential. Commands sent to Redis are first processed in memory. Then, if persistence is set up on these instances, there is a forked process on some interval that facilitates data persistence RDB (very compact point-in-time representation of Redis data) snapshots or AOF (append-only files).

These two flows allow Redis to have long-term storage, support various replication strategies, and enable more complicated topologies. If Redis isn't set up to persist data, data is lost in case of a restart or failover. If the persistence is enabled on a restart, it loads all of the data in the RDB snapshot or AOF back into memory, and then the instance can support new client requests.

With that said, let us look into more distributed Redis setups you might want to use.

In Redis, **RDB** and **AOF** are two mechanisms for **persistence**, i.e., how Redis saves data to disk to survive restarts or crashes.

---
###### 🧱 1. RDB (Redis Database File)
📦 What it is:
- A **snapshot** of the entire Redis dataset taken at specified intervals.
- Saved to a file called `dump.rdb`.
⚙️ How it works:
- Redis forks a background process to **serialize all in-memory data to disk**.

✅ Pros:
- **Compact**, single file — great for backups or replication.
- **Fast to load** on Redis startup.
❌ Cons:
- Data can be **lost since last snapshot** (e.g., if Redis crashes just before a snapshot).
- Forking for snapshots can use CPU & memory.
---
###### 📜 2. AOF (Append-Only File)
📦 What it is:
- A **log of all write operations**, stored chronologically in a file called `appendonly.aof`.
- Like a "journal" of every `SET`, `INCR`, etc.
⚙️ How it works:
- Redis appends every write command to the AOF file.
- File can grow large, so Redis **compacts it** using AOF rewrite.
AOF write policies (`appendfsync` in `redis.conf`):
- `always` — sync to disk on every write (safest, slowest)
- `everysec` — sync every second (default, good balance)
- `no` — let OS handle syncing (fastest, least safe)
✅ Pros:
- **Much safer than RDB** — at most 1 second of data loss (or less with `always`)
- **Replays commands**, so easier to debug.
❌ Cons:
- **Larger file size**.
- **Slower startup** (must replay all commands).
- More disk I/O.
---
🔁 Can I use both?
✅ Yes — and it’s recommended in many cases.
Redis handles both:
1. Uses RDB for fast startup.
2. Replays AOF to catch up to latest state.

##### Redis HA

![Redis with secondary failover.](https://substackcdn.com/image/fetch/w_1456,c_limit,f_auto,q_auto:good,fl_progressive:steep/https%3A%2F%2Fsubstack-post-media.s3.amazonaws.com%2Fpublic%2Fimages%2F9f32b62a-1676-4223-9cb2-688b49886df6_864x476.png "Redis with secondary failover.")

Redis with secondary failover. 

Another popular setup with Redis is the main deployment with a secondary deployment that is kept in sync with replication.  As data is written to the main instance it sends copies of those commands, to a replica client output buffer for secondary instances which facilitates replication. The secondary instances can be one or more instances in your deployment. These instances can help scale reads from Redis or provide failover in case the main is lost.

> **High availability** (**HA**) is a characteristic of a system that aims to ensure an agreed level of operational performance, usually uptime, for a higher than average period.
> 
> In these HA systems, it is essential to not have a single point of failure so systems can recover gracefully and quickly. This results in reliable crossover, so data isn't lost during the transition from primary to secondary, in addition to automatically detecting failure and recovery from it.

There are several new things to consider in this topology since we have now entered a distributed system that has many [fallacies](https://architecturenotes.co/fallacies-of-distributed-systems/) you need to consider. Things that were previously straightforward are now more complex
###### Redis Replication
The replication here is asynchronous. Redis replication uses a **replication ID** and an **offset** to track synchronization between a primary and its replicas. The replication ID identifies the source of data changes, while the offset counts how many commands have been processed. When a replica falls slightly behind the primary, and both share the same replication ID, the primary can send just the missing commands (partial sync) to catch up. However, if the replication IDs differ or the primary doesn't recognize the replica's offset, a full sync is required—this involves the primary sending a fresh snapshot (RDB) and buffering new changes during the transfer. Replication IDs change when a node is promoted to primary, but Redis remembers previous IDs to help replicas reconnect more efficiently. This mechanism allows Redis to avoid full syncs whenever possible by identifying common replication history and using offsets to resume from the correct point.

##### Redis Sentinel

![Redis Sentinel deployment (extra monitoring/dashed lines from other sentinel nodes are left out for clarity).](https://substackcdn.com/image/fetch/w_1456,c_limit,f_auto,q_auto:good,fl_progressive:steep/https%3A%2F%2Fsubstack-post-media.s3.amazonaws.com%2Fpublic%2Fimages%2F4204096c-5292-4825-bbb1-d56101d76ef7_1050x1168.png "Redis Sentinel deployment (extra monitoring/dashed lines from other sentinel nodes are left out for clarity).")

Redis Sentinel deployment (extra monitoring/dashed lines from other sentinel nodes are left out for clarity).

**Redis Sentinel** is a high-availability system provided by Redis to **monitor, notify, and automatically failover** Redis instances in a distributed setup. It helps ensure that your Redis deployment remains available even if a **primary node fails**.

Sentinel is a distributed system. As with all  distributed systems, Sentinel comes with several advantages and disadvantages. Sentinel is designed in a way where there is a cluster of sentinel processes working together to coordinate state to provide high availability for Redis. After all you wouldn't want the system protecting you from failure to have its own single point of failure.

Sentinel is responsible for a few things. 
1. It ensures that the current main and secondary instances are functional and responding. This is necessary because sentinel (with other sentinel processes) can alert and act on situations where the main and/or secondary nodes are lost.
2. It serves a role in service discovery much like Zookeeper and Consul in other systems. So when a new client attempts to write something to Redis,  Sentinel will tell the client what current main instance is.
So sentinels are constantly monitoring availability and sending out that information to clients so they are able to react to them if they indeed do failover.

Here are its responsibilities:
1. Monitoring **—** ensuring main and secondary instances are working as expected.
2. Notification **—** notify system admins about occurrences in the Redis instances.
3. Failover management — Sentinel nodes can start a failover process if the primary instance isn't available and enough (quorum of) nodes agree that is true.
4. Configuration management — Sentinel nodes also serve as a point of discovery of the current main Redis instance.

Using Redis Sentinel in this way allows for failure detection. This detection involves multiple sentinel processes agreeing that current main instance is no longer available. This agreement process is called Quorum. This allows for increased robustness and protection against one machine misbehaving and being unable to reach the main Redis node.

> Quorum
> A **quorum** is the minimum number of votes that a distributed system has to obtain in order to be allowed to perform an operations like failover. This number is configurable, but should be reflective of the number of nodes in said distributed system. Most distributed systems have sizes of three or five with quorums of two and three respectively. Odd number of nodes is preferred in cases the system is required to break ties.

This setup isn't without its disadvantages so we are going to run through a few  recommendations and best practices when using Redis Sentinel.

You can deploy Redis Sentinel in several ways. As general guidance I would recommend running a sentinel node along aside each of your application servers (if possible) so you also don't need to factor in network reachability differences between sentinel nodes and clients who are actually using Redis.

You can run Sentinel alongside the Redis instances or even on independent nodes, but that complicates things in different ways. I recommend at least running three nodes with a quorum of at least two. Here is a simple chart breaking down numbers of servers in a cluster and associated quorum and tolerated failures that are sustainable.

![](https://substackcdn.com/image/fetch/w_1456,c_limit,f_auto,q_auto:good,fl_progressive:steep/https%3A%2F%2Fsubstack-post-media.s3.amazonaws.com%2Fpublic%2Fimages%2F9eae8604-8b07-43ba-8cc4-0a83502cde0a_1592x772.png)

Table of number of servers and quorum with number of tolerated failures.

This will vary from system to system but general idea stands.

Let's take a moment to think through what could go wrong in such a setup.  If you  run this system long enough, you will run into all of them.
1. What if the sentinel nodes fall out of quorum?
2. What if there is a network split which puts the old main instance in the minority group? What happens to those writes? (Spoiler: they are lost when the system recovers fully)
3. What happens if the network topologies of sentinel nodes and client nodes (application nodes) are misaligned? 😬

There are no durability guarantees, especially since persistence (see below) to disk is asynchronous. There is also the nagging problem of _when_ clients find out about new primaries, how many writes did we lose to an unaware primary? Redis recommends that when new connections are established that they should query for the new primary. Depending on the system configuration, that could mean a significant data loss.

There are a few ways to mitigate the level of losses if you force the main instance to replicate writes to a minimum of one secondary instance. Remember, all Redis replication is asynchronous and has its trade-offs. So it will need to independently track acknowledgement and if they aren't confirmed by at least one secondary, the main instance will stop accepting writes.

##### Redis Cluster

![Redis Cluster Architecture](https://substackcdn.com/image/fetch/w_1456,c_limit,f_auto,q_auto:good,fl_progressive:steep/https%3A%2F%2Fsubstack-post-media.s3.amazonaws.com%2Fpublic%2Fimages%2F903484b2-8c0c-4ce9-b4ab-e967538aeb78_1972x1197.jpeg "Redis Cluster Architecture")

Redis Cluster Architecture

I am sure many have thought about what happens when you can't store all your data in memory on one machine. Currently, the maximum RAM available in a single server is 24TIB, presently listed online at AWS. Granted, that's a lot, but for some systems, that isn't enough, even for a caching layer.

>Vertical and Horizontal Scaling
> As your systems grow, you have three options.
> 1. Do less (No one does this entirely because we are insatiable monsters).
> 2. Scale up.
> 3. Scale out.
>     
> Taking the latter two seriously, scaling up and scaling out are known as vertical and horizontal scaling, respectively. Vertical scaling is a technique where you get bigger and better machines to do the work faster and hope all your problems scale well with your hardware. Even if this is possible, you will eventually be limited by the hardware you use.
> 
> Once you reach that point (more likely and hopefully way before), you will need to scale your system horizontally by spreading the workload across multiple smaller machines responsible for smaller parts of the whole.

So let's get some terminology out of the way; once we decide to use Redis Cluster, we have decided to spread the data we are storing across multiple machines, known as sharding. So each Redis instance in the cluster is considered a shard of the data as a whole.

This brings about a new problem. If we push a key to the cluster, how do we know which Redis instance (shard) is holding that data? There are several ways to do this, but Redis Cluster uses **algorithmic sharding**.

To find the shard for a given key, we hash the key and mod the total result by the number of shards. Then, using a **deterministic hash function**, meaning that a given key will always map to the same shard, we can reason about where a particular key will be when we read it in the future.

What happens when we later want to add a new shard into the system? This process is called resharding.

Assuming the key 'foo' was mapped to shard zero after introducing a new shard, it may map to shard five. However, moving data around to reflect the new shard mapping would be slow and unrealistic if we need to grow the system quickly. It also has adverse effects on the availability of the Redis Cluster.

Redis Cluster has devised a solution to this problem called Hashslot, to which all data is mapped. There are 16K hashslot. This gives us a reasonable way to spread data across the cluster, and when we add new shards, we simply move hashslots across the systems. By doing this, we just need to move hashslots from shard to shard and simplify the process of adding new primary instances into the cluster.

This is possible without any downtime, and minimal performance hit. Let's talk through an example.

M1 contains hashslots from 0 to 8191.

M2 contains hashslots from 8192 to 16383.

So to map `foo', we take a deterministic hash of the key (foo) and mod it by the number of hash slots(16K), leading to a mapping of M2. Now let's say we add a new instance, M3. The new mappings would be

M1 contains hashslots from 0 to 5460.

M2 contains hashslots from 5461 to 10922.

M3 contains hashslots from 10923 to 16383.

All the keys that mapped the hashslots in M1 that are now mapped to M2 would need to move. But the hashing for the individual keys to hashslots wouldn't need to move because they have already been divided up across hashslots. So this one level of misdirection solves the re-sharding issue with algorithmic sharding.

###### Gossiping

Redis Cluster uses gossiping to determine the entire cluster's health. In the illustration above, we have 3 M nodes and 3 S nodes. All these nodes constantly communicate to know which shards are available and ready to serve requests. If enough shards agree that M1 isn't responsive, they can decide to promote M1's secondary S1 into a primary to keep the cluster healthy. The number of nodes needed to trigger this is configurable, and it is essential to get this right. If you do it improperly, you can end up in situations where the cluster is split if it cannot break the tie when both sides of a partition are equal. This phenomenon is called split brain. As a general rule, it is essential to have an odd number of primary nodes and two replicas each for the most robust setup.

#### Redis Persistence Models

If we are going to use Redis to store any kind of data for safe keeping, it's important to understand how Redis is doing it. There are many use cases where if you were to lose the data Redis is storing is not the end of the world. Using it as a cache or in situations where its powering real-time analytics where if data loss occurs its no the end of the world.

In other scenarios, we want to have some guarantees around data persistence and recovery.

> ⏩ Redis is fast and all consistency guarantees, come second to speed. This maybe a controversial topic, but it is true. 


![Redis persistence models](https://substackcdn.com/image/fetch/w_1456,c_limit,f_auto,q_auto:good,fl_progressive:steep/https%3A%2F%2Fsubstack-post-media.s3.amazonaws.com%2Fpublic%2Fimages%2F3253ad9a-88dc-4f17-a70f-78b390e03012_2000x415.jpeg "Redis persistence models")
Redis persistence models

##### No persistence

**No persistence**: If you wish, you can disable persistence altogether. This is the fastest way to run Redis and has no durability guarantees.

##### RDB Files

**RDB** (Redis Database): The RDB persistence performs point-in-time snapshots of your dataset at specified intervals.

The main downside to this mechanism is that data between snapshots will be lost. In addition, this storage mechanism also relies on forking the main process, and in a larger dataset, this may lead to a momentary delay in serving requests. That being said, RDB files are much faster being loaded in memory than AOF.

##### AOF

**AOF** (Append Only File): The AOF persistence logs every write operation the server receives that will be played again at server startup, reconstructing the original dataset.

This way of ensuring persistence is much more durable than RDB snapshots since it is an append-only file. As operations happen, we buffer them to the log, but they aren't persisted yet. This log consists of the actual commands we ran in order for replay when needed.

Then when possible, we flush it to disk with fsync (when this runs is configurable), it will be persisted. The downside is that the format isn't compact and uses more disk than RDB files.

> #### fsync
> 
> **fsync**() transfers ("flushes") all modified in-core data of (i.e., modified buffer cache pages for) the file referred to by the file descriptor _fd_ to the disk device (or other permanent storage device) so that all changed information can be retrieved even if the system crashes or is rebooted.
> 
> For various, reasons when changes are made to file they are done to caches and calls to fsync() ensure they are persisted to disk and accessible later.

##### Why not both?

**RDB + AOF**: It is possible to combine AOF and RDB in the same Redis instance. If durability in exchange for some speed is a tradeoff, you are willing to make it. I think this is an acceptable way to set up Redis. In the case of a restart, remember that if both are enabled, Redis will use AOF to reconstruct the data since it's the most complete.

#### Use cases of Redis
##### Cache

[![cache](https://media2.dev.to/dynamic/image/width=800%2Cheight=%2Cfit=scale-down%2Cgravity=auto%2Cformat=auto/https%3A%2F%2Fdev-to-uploads.s3.amazonaws.com%2Fuploads%2Farticles%2F1fmckci0nmj5aptppl0t.png)](https://media2.dev.to/dynamic/image/width=800%2Cheight=%2Cfit=scale-down%2Cgravity=auto%2Cformat=auto/https%3A%2F%2Fdev-to-uploads.s3.amazonaws.com%2Fuploads%2Farticles%2F1fmckci0nmj5aptppl0t.png)

Since Redis stores data in memory, it serves as a **lightning-fast cache**. It uses a key-value structure where application data is mapped directly to Redis keys and values. In a clustered setup, Redis distributes these keys across multiple nodes, making it easy to **scale horizontally** — simply add more nodes when more capacity is needed.

When a request is made, the server **checks Redis first** before querying the database, reducing response time significantly. Redis supports eviction strategies like **Least Recently Used (LRU)** and allows setting a **TTL (Time To Live)** for each key. This ensures stale data is automatically removed, and helps keep the cache size under control even when the dataset is larger than available memory.

However, **hot keys** — keys that are accessed very frequently — can cause performance bottlenecks. This is a known challenge not only in Redis, but also in other caching systems like Memcached and DynamoDB.

###### 📦 Common Caching Patterns with Redis
1. **Read-Through Cache**
	- Application checks Redis first.
	- If key is missing (cache miss), load from DB, store in Redis, then return.

2. **Write-Through Cache**
	- Application writes to both DB and Redis.
	- Keeps cache fresh but increases write latency.
	
3. **Cache Aside (Lazy Loading)** – most common
	- App reads from Redis. 
	- On a miss, it fetches from DB and writes to Redis.
	- On write/update, update DB and **invalidate** the cache key.
	
4. **Write-Behind (Asynchronous)**
	- App writes to cache first.
	- DB update is done **asynchronously**.
	- Faster but risks data loss on crash.

###### Redis supports built-in eviction strategies when memory is full:
- `noeviction`: Returns error when memory is full (default).
- `allkeys-lru`: Evict least recently used keys.
- `volatile-lru`: Evict least recently used **only among keys with TTL**.
- `allkeys-random`: Evict random keys.
- `volatile-ttl`: Evict keys with soonest expiration.
##### Distributed lock

Redis can be used to implement **distributed locks**, which help coordinate access to shared resources across multiple processes, threads, or even machines in a distributed system. It's commonly used when you want **mutual exclusion** — ensuring that **only one node is working on a task or holding a resource at a time**.

🔐 Basic Idea of a Redis Lock
Use a Redis `SET` command with special options to:
- Set a **key** representing the lock.
- Give it a **value** that uniquely identifies the lock holder.
- Set a **TTL (expiration time)** to avoid deadlocks.
- Only set it **if it doesn’t exist** (`NX` option).

✅ Example (Simplified Lock)
`SET lock_key unique_value NX PX 10000`

- `lock_key`: the name of the lock (e.g., "user:123:lock").
- `unique_value`: a UUID or thread ID (used later to verify the owner).
- `NX`: only set if the key does not exist (no one else holds the lock).
- `PX 10000`: set expiration to 10 seconds (auto-release if process crashes).

If the result is `"OK"` → you **acquired the lock**.

🔓 Releasing the Lock
To release the lock, only the owner (i.e., the one who set it) should be allowed to delete it.
So before deleting, you check that the value matches.

❗ Pitfalls of Naive Redis Locks
- Clocks can drift → TTL may expire too soon or too late.
- Without proper ownership checks, another process could accidentally release your lock.
- Network partition could lead to **multiple clients believing they hold the lock** (split-brain).

✅ Redlock Algorithm (Robust Distributed Locking)

Redis creator **Antirez (Salvatore Sanfilippo)** proposed Redlock, a **safer** distributed lock across multiple Redis nodes.

🔑 Redlock steps:
1. Try to acquire the same lock on **N independent Redis instances** (e.g., 5).
2. Use the same unique value and TTL.
3. Wait for **a majority (e.g., 3/5)** to grant the lock **within a time window**.
4. Only then consider the lock acquired.
5. Release the lock from all instances after use.

This provides **better fault tolerance**, avoiding split-brain scenarios.

##### Rate Limiter

Redis is commonly used to implement **rate limiting** — controlling how many requests a user or client can make in a given time window. It’s perfect for this use case because it's **fast**, **atomic**, and supports **TTL (expiration)** natively.

✅ Typical Use Case

> "Allow up to 100 requests per user per minute."

Redis helps **track request counts per user** and **reset them automatically** using TTL.

---
###### 🔧 Common Redis Rate Limiting Patterns

1. **Fixed Window Counter**

	- Create a Redis key per user and time window (e.g., per minute).
	- Use `INCR` to count requests.
	- Set TTL to expire after the time window.

Example:

```
INCR user:123:rate
EXPIRE user:123:rate 60
```
If the counter exceeds the limit (e.g. 100), block the request.
⚠️ _Problem:_ All requests at the window's end are allowed again suddenly (burstiness).

2. **Sliding Window Log (Accurate but Expensive)**
	- Store each request timestamp in a **list or sorted set**.
	- For each request:
	    - Remove timestamps older than window.
	    - Check how many remain.
	    - Allow or reject based on count.

⚠️ _Problem: More accurate but higher memory and CPU usage.

3. **Sliding Window Counter (Smoothed)**

	- Approximate sliding window with a weighted average of counters in two adjacent windows.
	- More efficient than logs, smoother than fixed windows.

4. **Token Bucket (Most Flexible)**

	- Refill tokens at a fixed rate.
	- Each request consumes a token.
	- If tokens are exhausted, block the request.

##### Redis in ranking/leader board system

Redis is an excellent fit for building **ranking or leaderboard systems**, thanks to its **Sorted Set (ZSET)** data structure, which supports **automatic ordering by score**.

🧱 What is a Redis Sorted Set?
A **Sorted Set** in Redis stores:
- **Unique members** (like usernames, player IDs)
- Each with an associated **score** (like points, ranks, time)

Redis maintains the **members in sorted order by score**, and provides fast access to:
- Rank of a member
- Top-N members
- Score updates
- Range queries (e.g., top 10, users between rank 50–100)

📊 Leaderboard Use Case: Game Scores

🔧 Add/Update a Player’s Score

```
ZADD game:leaderboard 1500 player1
ZADD game:leaderboard 2000 player2
```

📈 Get Top N Players

```
ZREVRANGE game:leaderboard 0 9 WITHSCORES
```
- `ZREVRANGE`: returns members **from highest to lowest**
- `0 9`: top 10
- `WITHSCORES`: include their scores

🔍 Get a Player’s Rank

```
ZREVRANK game:leaderboard player1
```
Returns 0-based rank (higher score = lower rank number)

🔄 Increment Score

```
ZINCRBY game:leaderboard 300 player1
```
Adds 300 to player1’s existing score

📉 Get Players Within a Score Range

```
ZRANGEBYSCORE game:leaderboard 1000 2000
```
Returns players whose scores are between 1000 and 2000

🚀 Why Use Redis for Leaderboards?

- **Fast reads/writes** (all operations in O(log N))
- **Sorted set handles ordering natively**
- **Supports TTL** (e.g., daily/weekly reset)
- **Can paginate, rank, filter by score** efficiently
- **Scalable** via clustering (partition by game or region)

⚖️ Limits and Considerations

- Sorted sets scale well up to **millions of items**
- Each member adds ~50–100 bytes of memory overhead
- Redis Cluster can scale beyond one machine by **sharding by key**
- **No built-in deduplication** across multiple sorted sets
- Snapshots or persistence (RDB/AOF) needed for backup

##### Redis Streams
Redis Streams are an advanced data structure introduced in Redis 5.0 that allows you to manage and process streams of data in real-time.  
They provide an append-only log of messages with an efficient way to consume and process these messages.

**Fundamental components of Redis Streams:**

- **Stream**: A stream in Redis is similar to a log or a message queue. it stores sequence of messages (entries), each identified by a unique ID and consisting of key-value pairs.

**It is immutable**
Streams are ==immutable==. The ordering of elements cannot be changed. This is because a stream is a continuous flow of data or information. It represents a sequence of elements, where elements are added to the end of the sequence over time. Therefore, you can only add new data to the end of a stream.

![](https://miro.medium.com/v2/1*l7XVk0EfK9RuqLtaa2WOAw.gif)


- **Entries**: Each entry in a stream is a message that contains data. Entries are added with a unique ID, which can be either auto-generated or specified manually. The Streams are stored in-memory and are backed up through persistence (AOF or RDB file). Redis Streams are high-performing since all the operations are in-memory operations and avoids any disk I/O.

- **Consumer Groups**: Redis Streams support consumer groups, allowing multiple consumers to read and process entries from the same stream. This enables distributed and parallel processing of data.

![](https://substackcdn.com/image/fetch/w_1456,c_limit,f_auto,q_auto:good,fl_progressive:steep/https%3A%2F%2Fsubstack-post-media.s3.amazonaws.com%2Fpublic%2Fimages%2Ff11dd5d6-af1f-462a-b8f8-0f2114c22f8b_1914x576.png)

###### Streams can be processed by multiple consumers.

Redis provides two ways for streams to be consumed.

The first one is allowing multiple consumers to read the same data from any point in the stream. This is similar to showing your boarding pass to multiple people at different sections of the airport.

First, you’re going to present your ticket to a security guard to be allowed in the boarding zone. Then, you’re going to show your ticket to an airline employee to be allowed on the plane. Multiple people validate the same information.

When you think about applications, we could have multiple applications validating the same tickets and each one doing its own processing on it.

![](https://miro.medium.com/v2/resize:fit:875/1*C4HKEyHQnF0ci2xxMt-mxA.gif)

Which is convenient. But what if you have millions of tickets and you want to scale your applications horizontally? You need replicas of your application, all of them performing the same validations.

At the same time, you don’t want the replicas to validate the same tickets. You want each replica to validate a different ticket so that you can process more tickets at a time.

To enable efficient and distributed processing of data, Redis Streams adopted the concept of Consumer Groups.

Consumer groups are a way to organize multiple consumers that work together to process data from a stream. They help distribute the work among different consumers, allowing them to share the load and process the data more efficiently.

![](https://miro.medium.com/v2/resize:fit:875/1*Pot-PUOytmGaE4vNsKvfhw.gif)

Each replica of the same application will be responsible for processing a different ticket. This allows all tickets to be processed more efficiently.

###### Publishing to a Stream

A publisher in Redis Streams is the agent responsible for adding new elements to the stream. In this role, the publisher is central to real-time data processing. By publishing data to a stream, other parts of your application (the consumers) can react to that data almost instantly.

To add new elements to the stream, we can use the XADD command. It appends an element consisting of key-value pairs to the specified stream.

If the stream doesn’t exist, Redis will create it automatically.

The command also generates a unique identifier for the element, which is based on the current timestamp and a sequence number by default.

![](https://miro.medium.com/v2/resize:fit:875/1*XIy6JGa21sbVre8WkImGdw.png)

Breaking down this command, we have:

- **Key of the stream**: The name of the stream you want to add the element to.
- **ID**: The identifier for the element. You can use the special character * to let Redis automatically generate an ID based on the current timestamp and a sequence number.
- **Element in the Stream**: The key-value pairs that make up the element.

As a publisher, you can add elements to the stream continuously. For instance, in the context of a concert, the ticket scanner could be the publisher. As it scans each ticket, it could publish an element to the stream, including information like the ticket ID, seat number, and element time. Other parts of the system could consume these messages in real-time, for instance, to keep track of the occupied and available seats.

Here is an example of adding a new element to the ‘tickets’ stream:
```
XADD tickets * name "Raphael De Lio" seat "B12" movieId 53 sessionId 832
```

By entering this command, Redis will respond with the generated ID for the new element, which can be used later to read or reference the element in the stream.

```
1678900012656-0
```

The above element can then be consumed by a consumer or consumer group for further processing.

Publishers play a crucial role in Redis Streams, facilitating real-time data processing by pushing new elements to the stream. They are not constrained by the number of consumers; they can continue publishing irrespective of the number of consumers or the speed at which consumers are processing elements.

###### Consuming from a stream

In a real-time processing scenario, it’s not just about adding elements to the stream but also reading them, analyzing the data, and possibly taking some action based on that data. That’s where stream consumers come into play.

A consumer in Redis Streams reads the data from the stream and processes it. There can be multiple consumers reading from the same stream simultaneously, which allows for parallel processing of the data.

To read elements from a stream, we use the XREAD command.

![](https://miro.medium.com/v2/resize:fit:875/1*81aUe5JNsWvoFWW3bcREdA.png)

Breaking down this command, we have:

- **COUNT count:** This is optional and is used to limit the number of messages returned by the command.
- **BLOCK milliseconds:** The `BLOCK` option in the `XREAD` command is optional and sets a timeout in milliseconds for how long the command should wait if no data is available. If the timeout expires and no data is received, the command returns an empty response.
- **STREAMS stream_name:** The name of the streams from which you want to read elements.
- **ID:** The ID from which to start reading elements. If you want to read all elements, you can use “0–0” as the ID.

This command will return all elements in the “tickets” stream. And the reason why we use 0–0 to read from the beginning of the stream is that the IDs are incremental by default. They are a combination of timestamps and sequence numbers `(1678900012656-0)`. 0–0 would be the lowest one.

But if you want to listen only to new messages, you can use the special $ ID with the BLOCK option. The $ ID will only return messages that have been added to the stream after the XREAD command was sent.

![](https://miro.medium.com/v2/resize:fit:875/1*ps7Bdnau1bpnPWuO3r2evA.png)

So, it is important to understand that you should use the $ ID only for the first call to XREAD. Later the ID should be the one from the last reported element in the stream. Otherwise, you could miss all the entries that are added in between.

###### Stream Consumer Groups

As mentioned earlier, Redis Streams allows us to create Consumer Groups. They are particularly useful when the rate of incoming data is high or the processing time per message is significant.
Consumer groups allow you to distribute the data processing among multiple consumers while making sure they won’t read the same elements.

###### Creating a Consumer Group

First, you need to create a consumer group associated with the stream using the XGROUP command.

![](https://miro.medium.com/v2/resize:fit:875/1*NFZG2nivSkg0k-joR8kgZg.png)

The parameters are as follows:

- **stream_name:** The name of the streams you want to associate with the group.
- **group_name:** The name you want to give to the consumer group.
- **ID:** The ID from which the group will start reading. If you want the group to read all elements since the beginning, use “0” as the ID. You can also use `$` ID for reading only elements that were added after the group was created.

###### Reading from a Consumer Group

To read data from a consumer group, you use the XREADGROUP command. This command is similar to the XREAD command but includes the consumer group and consumer name as additional parameters.

![](https://miro.medium.com/v2/resize:fit:875/1*YD7At_UnmBSenkHguTSDtQ.png)

The command reads as follows:

- **Consumer Group Name:** the name of the group you want to read from.
- **Name of the consumer:** the name you want to give to this consumer.
- **Key of the Stream:** name of the streams you want this consumer to read from.
- **ID:** The ID from which the group will start reading. If you want the group to read all elements since the beginning, use “0” as the ID. You can also use `$` ID for reading only elements that were added after the group was created. In this case, `>` means that the consumer should read new elements that haven’t been read by any other consumer in the group.

###### Acknowledging Messages with XACK

Once a message is processed by a consumer, it needs to be acknowledged. This is done with the XACK command.

This command removes the message from the Pending Entries List (PEL) of the group, indicating that the message has been processed successfully and does not need to be delivered again.

```
XACK stream_name group_name message_id
```

An example of acknowledging a message with the id ‘1526569495631–0’ from the “tickets” stream in the “officer_group” consumer group would be:

```
XACK tickets officer_group 1526569495631–0
```

###### Claiming Stalled Messages with XPENDING, XCLAIM and XAUTOCLAIM

If a consumer fails to acknowledge a message within a specified time, it is assumed that the processing of the message has failed.

A list of pending to-be-acknowledged messages can be returned with the **XPENDING** command:

```
XPENDING key group [[IDLE min-idle-time] start end count [consumer]]
```

An example would be of checking which messages are pending to be acknowledged for more than 30 seconds on the tickets stream within the officer_group group:

```
XPENDING tickets boarding_counter IDLE 30000 - + 10 consumer2
```

Another consumer can claim ownership of these messages using the **XCLAIM** command. This command changes the ownership of the message from the original consumer to a new consumer.

```
XCLAIM stream_name group_name new_consumer min_idle_time message_id
```

A simple example of a consumer named “consumer3” claiming a message with the id ‘1526569495631–0’ from the “tickets” stream in the “officer_group” group would be:

```
XCLAIM tickets officer_group consumer3 30000 1526569495631–0
```

This command claims messages that have been idle for at least 30 seconds (30000 milliseconds).

**Remember, Redis doesn’t automatically execute XCLAIM or XACK when using XREADGROUP. These are manual operations that need to be performed by the consumer.**

###### **Common Use Cases**

Redis Streams are highly versatile and can be applied to a variety of real-time data processing scenarios. Here are some common use cases where Redis Streams shine:
###### Real-Time Analytics Dashboards

Redis Streams can be used to build real-time analytics dashboards that provide up-to-the-minute insights. For example, a website monitoring service might use Redis Streams to collect and display data on user interactions, page load times, and error rates. This allows businesses to quickly identify and respond to issues as they occur.

###### Event Sourcing in Microservices Architectures

In a microservices architecture, event sourcing is a pattern where state changes are stored as a sequence of events. Redis Streams are an excellent fit for this pattern, as they provide a reliable, ordered log of events. Services can subscribe to the stream to react to state changes, ensuring that the system remains consistent and scalable.

###### Stream Processing Pipelines

Redis Streams are ideal for building stream processing pipelines, where data flows through a series of processing stages. For example, in an IoT application, data from sensors might be ingested into a Redis Stream, processed by various consumer services (e.g., filtering, aggregation, anomaly detection), and finally stored in a database or visualized in a dashboard. This architecture enables scalable and flexible data processing.
###### Message Queues

Redis Streams can function as a message queue, providing a way to decouple producers and consumers in an application. Producers can add messages to the stream, and multiple consumers can process these messages independently. This is useful in scenarios like job processing systems, where tasks need to be distributed among workers for parallel execution.

###### Log Aggregation

Log aggregation involves collecting and processing logs from various sources in a centralized location. Redis Streams can be used to aggregate logs from different servers or applications, allowing for real-time log analysis and monitoring. By leveraging Redis Streams, businesses can gain real-time visibility into their systems, enabling faster troubleshooting and incident response.

By exploring these use cases, it’s clear that Redis Streams offer a flexible and powerful solution for handling real-time data across a wide range of applications.

##### Pub/Sub

Pub/Sub stands for “Publish/Subscribe”. It is a pattern in computer programming that involves allowing messages to be sent from one component of an application to one or many other components without those components being directly connected or having a direct relationship with one another.

Think of it like a radio broadcaster. The “publisher” broadcasts audio, and the “subscribers” listen to the audio. The publisher does not need to know who the subscribers are, and the subscribers do not need to know who the publisher is. The only thing that matters is the message being broadcast.

![Animated GIF of a broadcasting tower emitting signals, represented by concentric lines radiating outward, symbolizing communication or data transmission.](https://miro.medium.com/v2/resize:fit:875/1*QqYUDiMS8G09c8ulpRerFQ.gif)

In pub/sub, components can send messages to a central “topic” or “channel”, and other components can subscribe to that channel to receive those messages. This allows for decoupled communication between components and makes it easier to manage the flow of information in a complex system.

###### Pub/Sub in Redis

Redis implements the Pub/Sub pattern by providing a simple and efficient messaging system between clients. In Redis, clients can “publish” messages to a named channel, and other clients can “subscribe” to that channel to receive the messages.

When a client publishes a message to a channel, Redis delivers that message to all clients that are subscribed to that channel. This allows for real-time communication and the exchange of information between separate components of an application.

![](https://miro.medium.com/v2/resize:fit:875/1*vIW3-iwDyYFwWCT-9IOnsQ.gif)

Redis Pub/Sub provides a lightweight, fast, and scalable messaging solution that can be used for various use cases, such as implementing real-time notifications, sending messages between microservices, or communicating between different parts of a single application.

###### Synchronous Communication

Redis Pub/Sub is synchronous. Subscribers and publishers must be connected at the same time in order for the message to be delivered.

Think of it as a radio station. You are able to listen to a station while you’re tuned into it. However, you’re incapable of listening to any message broadcast while your radio was off. ==Redis Pub/Sub will only deliver messages to connected subscribers.==

This means that if one subscriber loses connection and this connection is restored later on, it won’t receive any missed messages or be notified about them. Therefore, it limits use cases to those that can tolerate potential message loss.

![Animated GIF showing a Redis Channel broadcasting a message, ‘The match started! It’s Brazil against Argentina!’ from a sender at the top to three listeners below. Each listener reacts with comments like ‘Go Neymar!’, ‘Cool!’, and ‘Nice!’, symbolizing a publish-subscribe (Pub/Sub) messaging system in Redis.](https://miro.medium.com/v2/resize:fit:750/1*d4ILdGyY4_BhX8LSDvMunA.gif)

###### Fire & Forget

Fire & Forget is a messaging pattern where the sender sends a message without expecting an explicit acknowledgment from the receiver that the message was received. The sender simply sends the message and moves on to the next task, regardless of whether or not the message was actually received by the receiver.

![An animated GIF illustrating a Pub/Sub system. A publisher on the left sends messages to a mailbox in the center, representing a Redis channel. On the right, two subscribers react: one says, ‘I’m receiving the messages, but I cannot tell the receiver I am!’ while the other says, ‘I stopped receiving the messages, but the receiver doesn’t know about it,’ demonstrating the decoupling nature of Pub/Sub communication.](https://miro.medium.com/v2/resize:fit:875/1*r0WpQB5ZUDJqkxvL-RXk7Q.gif)

Redis Pub/Sub is considered a “Fire & Forget” messaging system because it does not provide an explicit acknowledgment mechanism for confirming that a message was received by the receiver. Instead, messages are broadcast to all active subscribers, and it is the responsibility of the subscribers to receive and process the messages.

###### Fan-out Only

Redis Pub/Sub is fan-out only, meaning that when a publisher sends a message, it is broadcast to all active subscribers. All subscribers receive a copy of the message, regardless of whether they are specifically interested in the message or not.

###### Publish and Subscribe

Redis message brokering is implemented through the **PUBLISH** and **SUBSCRIBE** commands. The **PUBLISH** command allows the user to send a message to a specific channel, and the **SUBSCRIBE** command allows the user to listen to messages on a specific channel. This makes it easy to implement a publish-subscribe pattern in your application.


##### Redis Pub/Sub vs Redis Streams

Both **Redis Pub/Sub** and **Redis Streams** allow messaging between services or components, but they serve different use cases and offer very different guarantees.

###### 🔁 Redis Pub/Sub (Publish-Subscribe)

🔹 What It Is:
	- A **fire-and-forget messaging model**.
	- Publishers send messages to a **channel**.
	- Subscribers listening on that channel receive messages **immediately**.

🔹 Key Characteristics:
	- **No persistence** — messages are lost if no one is listening.
	- **No message history or replay**.
	- **No delivery guarantees**.
	- **No consumer groups** — all subscribers get the message simultaneously.

###### 🔄 Redis Streams

🔹 What It Is:
	- An **append-only**, persistent log of messages (like Kafka-lite).
	- Messages are stored with unique **IDs**.
	- Supports **acknowledgments**, **consumer groups**, **retries**, **replay**.

🔹 Key Characteristics:
	- **Persistent** — messages are stored on disk.
	- Consumers can **read at their own pace**.
	- Supports **replay and recovery**.
	- Can scale using **consumer groups** for parallelism.
	- Requires **manual acknowledgment** (`XACK`) for at-least-once delivery.

###### 🆚 Key Differences

| Feature                | **Redis Pub/Sub**       | **Redis Streams**                          |
| ---------------------- | ----------------------- | ------------------------------------------ |
| **Persistence**        | ❌ No                    | ✅ Yes (durable log)                        |
| **Message Replay**     | ❌ Not possible          | ✅ Possible via `XREAD`, `XRANGE`           |
| **Delivery Guarantee** | ❌ None                  | ✅ At-least-once (with `XACK`)              |
| **Offline Consumers**  | ❌ Miss messages         | ✅ Can catch up later                       |
| **Consumer Groups**    | ❌ No                    | ✅ Yes (for scaling consumers)              |
| **Use Case**           | Real-time notifications | Event sourcing, job queues, data pipelines |
| **Message Format**     | Simple strings          | Field-value pairs (structured data)        |


##### Kafka vs Redis Streams

| Feature                   | **Redis Streams**                        | **Apache Kafka**                           |
| ------------------------- | ---------------------------------------- | ------------------------------------------ |
| **Persistence**           | Yes, in-memory with disk persistence     | Yes, disk-based (log-structured storage)   |
| **Scalability**           | Limited (single-threaded per stream key) | Massive horizontal scalability             |
| **Ordering Guarantees**   | Per stream                               | Per partition (stronger ordering control)  |
| **Delivery Semantics**    | At-least-once (manual ack required)      | At-least-once, exactly-once (with config)  |
| **Consumer Groups**       | Yes                                      | Yes (robust and well-integrated)           |
| **Replay Capability**     | Yes (manual replay using IDs)            | Yes (seek to offsets, time-based replay)   |
| **Retention Policy**      | Time-based or max length                 | Time-based or size-based (more flexible)   |
| **Partitioning**          | Manual, not native                       | Native, automatic partitioning             |
| **Multi-Tenant Usage**    | Less robust                              | Strong support (topics, ACLs, quotas)      |
| **Backpressure Handling** | Basic (can block readers)                | Built-in with batching and disk buffering  |
| **Throughput**            | Moderate (~100K msgs/sec)                | High (~millions of msgs/sec per broker)    |
| **Durability & HA**       | Good (with persistence + AOF)            | Strong (replication, leader election, ISR) |
| **Ecosystem & Tooling**   | Minimal                                  | Rich (Kafka Connect, Streams, Schema Reg)  |
| **Use Case Fit**          | Lightweight messaging, microservices     | Data pipelines, big data, analytics, logs  |

---

###### ✅ When You Should Use **Kafka** Instead of Redis Streams:

🔹 1. **High throughput and scalability**

Kafka can handle **millions of messages per second** with **horizontal scaling**, making it a better fit for **large-scale data pipelines**.

🔹 2. **Long-term storage and replay**

Kafka stores events on disk and allows consumers to replay messages by offset or timestamp — ideal for analytics, auditing, and state reconstruction.

🔹 3. **Strong partitioning & ordering**

Kafka offers **fine-grained control** over **data sharding (partitioning)** and **per-partition message order** — essential for large distributed systems.

🔹 4. **Exactly-once semantics (EOS)**

Kafka provides optional **exactly-once processing**, which Redis Streams does not natively support.

🔹 5. **Powerful ecosystem**

Kafka integrates with **Kafka Connect**, **Kafka Streams**, **ksqlDB**, and **hundreds of connectors** for databases, file systems, monitoring, etc.

 🔹 6. **Strong durability and fault tolerance**

Kafka’s multi-node clusters with **replication, leader elections, ISR**, and **Zookeeper/KRaft** enable high availability and recovery.

---

###### 🚀 When to Prefer **Redis Streams**:

- Lightweight, fast integration (no complex infra).
- Tight latency requirements (real-time pub-sub + stream combo).
- Simpler workloads or microservices that don’t need massive scale.
- You’re already using Redis for caching and want to reuse it for messaging.

##### Redis Geospatial

**Redis Geospatial** is a powerful feature that allows you to store, query, and manage **geographical locations** (latitude & longitude) in Redis. It’s ideal for applications like **ride-sharing**, **food delivery**, **store locators**, and **proximity-based searches**.

Redis implements geospatial data using sorted sets and the Geographic Hash (GEOHASH), an algorithm for encoding latitude-longitude pairs into a binary string. It simplifies proximity searches and distance calculations, all the while keeping a compact representation.

###### Adding Labeled Locations

You can add locations with the `geoadd()` function. This function takes two arguments, a name, and a tuple with the longitude, latitude, and label.

In the following examples, I am using locations of places I can purchase coffee nearby. This could be the starting point for an excellent and fun tool that would find me the nearest cup of coffee.

```
>>> R.geoadd("coffee", (-120.4089, 48.5937, "Mazama Store"))  
1  
>>> R.geoadd("coffee", (-120.1843, 48.4769, "Rocking Horse Bakery"))  
1  
>>> R.geoadd("coffee", (-120.1746, 48.4667, "Pony Espresso"))  
1
```

The `geoadd()` function returns a truthy value when a new element is added to the set. If the label already exists in the set the coordinates are updated. Please note that coordinates are given in _longitude, latitude_ order and not the expected _latitude, longitude_ order.

###### Searching

There are a confusing number of older search functions still provided by Redis for compatibility purposes. If you are writing new code or just learning you are better off simply using the `geosearch()` function, which while complex has all of the functionality you’ll need.

There are two basic ways you’ll use the `geosearch()` function. In both of them you specify a latitude and longitude. In the first you specify a radius, and locations within that radius will be returned. In the second you specify a height and a width and locations within that rectangle will be returned. The latter is used with map apis such as Google Maps and [leaflet.js](https://leafletjs.com/).

There are a bewildering number of parameters to `geosearch()`, and to do them justice it is best to visit [the redis-py command reference](https://redis-py.readthedocs.io/en/stable/commands.html). However, as a basic example the following gives you the nearest coffee to a given location:

```
>>> from pprint import pprint  
>>> pprint(R.geosearch(  
... "coffee", unit="km", radius=30, longitude=-120.1806, latitude=48.4728,  
... withcoord=True, withdist=True, sort="ASC"  
... ))  
[[b'Rocking Horse Bakery', 0.5315, (-120.18430083990097, 48.47690083540148)],  
[b'Pony Espresso', 0.8098, (-120.17460197210312, 48.46670111745631)],  
[b'Mazama Store', 21.5306, (-120.40889829397202, 48.593700786423824)]]
```

There is a lot to unpack here. Basically, what we did was search the “coffee” collection, using units of kilometers (“km”) and find all locations within 30 kilometers of the passed-in coordinates. Results include the location label, distance and the coordinates sorted in ascending order by distance.

So you can see that the Rocking Horse Bakery is the closest place with coffee to our coordinates, at about half a kilometer away.

Note that the coordinates of our locations are not exactly the same coordinates we passed with `geoadd()`. This is due to the representation of these coordinates with the geohash algorithm.

###### Other Operations

**Deletion**. You can delete labels using the `zrem()` function:

```
>>> R.zrem("coffee", "Pony Espresso")  
```

**Cardinality**. You can count how many labels are in a set with the `zcard()` function:

```
>>> R.zcard("coffee")
```

**Distance**. You can compute the distance between two labels with the `geodist()` function:

```
>>> R.geodist("coffee", "Mazama Store", "Rocking Horse Bakery")  
21033.1106
```

Units are in meters by default, you can use the `unit=` parameter to choose other units. This example asks the same question but asks for the distance in miles:

```
>>> R.geodist("coffee", "Mazama Store", "Rocking Horse Bakery", unit="mi")  
13.0694
```

**Location**. You can get the current coordinates of one or more labels with the `geopos()` function:

```
>>> R.geopos("coffee", "Mazama Store")  
[(-120.40889829397202, 48.593700786423824)]
```

###### Where to go for more information

Both the [Redis Commands Documentation](https://redis.io/commands/) and the [redis-py commands reference](https://redis-py.readthedocs.io/en/stable/commands.html) provide complete information. Unfortunately, outside of being in alphabetical order, that information is poorly organized and there are an enormous number of commands which makes it extremely intimidating for the novice. The one thing that will make that easier for you is that nearly all of the commands you are interested in start with “GEO” (Redis Commands) or “geo” (python functions). The other sometimes useful commands are the commands that manipulate sets, and they all start with “Z” (Redis Commands) or “z” (python functions).

###### Practical Applications

**Displaying markers on a map**. Both Google Maps API and [leaflet.js](https://leafletjs.com/) both support a markers abstraction. Markers are just a list of labeled coordinates that will be displayed on the map, and you can select an icon to be displayed on the map for each marker as well. Maps are typically represented by their center coordinates and their height and width, which closely and neatly match the parameters you can pass to `geosearch()`.

**Finding something nearby**. When you think about it, this is a very common operation you see on websites. Nearly any retail store chain will have a page that will find the nearest store to your location or zip code. Pretty much every automobile manufacturer will help you locate the nearest dealer and nearest service center to your location. This again can be easily accomplished with the `geosearch()` function.


