
### Resources:
1. https://codezym.com/
2. ⁠https://www.lldcoding.com/
3. ⁠⁠https://www.educative.io/courses/grokking-the-low-level-design-interview-using-ood-principles
4. https://github.com/ashishps1/awesome-low-level-design?tab=readme-ov-file#-low-level-design-interview-problems
5. https://enginebogie.com/public/charchaa/post/popular-low-level-design-problems-and-their-solutions/138?srsltid=AfmBOopU8AxIz-0fk9freidmMFA32YQr9hnBlvCglvKgLW9FLGC65gKa

### HLD Topics:

Geo-location based:
	Proximity Service
	Nearby Friends
	Google Maps
Distributed Message Queue
	- Kafka, RabbitMQ, SQS (are the messages stored for one-time consumption)
Metrics Monitoring and Alerting System
	- Prometheus - Pull/Push/Hybrid
	- Cortex DB (Time series DB)
	- Rules/Alerts
		- Stream Processing + Flink, etc.
Ad Click Event Aggregation
Hotel Reservation System
	- Distributed Locking?
Distributed Email Service
S3-like Object Storage
Real-time Gaming Leaderboard
	- Top K
Digital Wallet
Rate Limiter
	- LLD
	- Multithreaded?
Unique ID
	- Snowflake ID
URL Shortener
Notification System
Chat App
FB Live Comments
Autocomplete
Google Drive / Dropbox

**TODO:**

~~Key-Value Store / Dynamo~~
~~Live Streaming~~
~~Payment System~~
Distributed Sequencer
Web Crawler
~~News feed - FB/Instagram~~
Stock Exchange
~~Job Scheduler~~
Lambdas

### Questions

#### Design a Parking Lot - Single Thread

Design and implement a **multi-floor parking lot system** in Java or Python with:

- Two types of parking spots:
    - `2` for two-wheelers
    - `4` for four-wheelers
    - `0` for inactive (not usable) spots
- Each floor is a 2D grid of parking spots (rows × columns), and all floors have the same layout.

**Requirements**
- Initialize the parking lot structure with multiple floors and spot types
- Park a vehicle based on its type and strategy, and return the assigned spot ID.
- Use strategy 0 to assign the lowest-index free spot (floor, row, column) of the correct type.
- Use strategy 1 to assign a spot from the floor with the most free spots for that vehicle type.
- Track and map each parked vehicle's number and ticket ID to its spot ID.
- Unpark a vehicle from a given spot ID and update availability accordingly.
- Search for a vehicle using its number or ticket ID and return its current spot ID.
- Return the current count of free parking spots for a specific vehicle type on a given floor.


#### HLD

##### Tagging Service Or HashTag Service
https://www.youtube.com/watch?v=zskh3kq8xZc

### Study
  
► Design a SQL-backed KV Store  
∟ Focus on: relational schema modeling + CRUD latency tradeoffs  
  
► Design a Superfast KV Store  
∟ Focus on: in-memory caching + fast persistence strategies  
  
► Design a Faster Superfast KV Store  
∟ Focus on: optimizing for write-heavy workloads at scale  
  
► Design S3 (Object Storage)  
∟ Focus on: chunking, metadata handling, and eventual consistency  
  
► Design a Distributed Cache  
∟ Focus on: eviction policies, replication, and cache invalidation  
  
5. Realtime & Event-Driven Systems  
  
► Design Online/Offline Indicator  
∟ Focus on: heartbeat mechanisms + stale state detection  
  
► Design a Realtime Database  
∟ Focus on: websocket handling + conflict resolution  
  
► Design Synchronized Queue Consumers  
∟ Focus on: concurrency, message ordering, and consumer coordination  
  
► Design Flash Sale  
∟ Focus on: load shedding, queueing, and atomic inventory updates  
  
► Design Realtime Claps  
∟ Focus on: low-latency counter updates without write bottlenecks  
  
6. User-Facing Apps & Social Systems  
  
► Design a Blogging Platform  
∟ Focus on: data modeling, feed generation, and access control  
  
► Design OnePic (Photo App)  
∟ Focus on: media storage, timelines, and user-generated content  
  
► Design Photo Tagging  
∟ Focus on: graph relationships and search within images  
  
► Design HashTag Service  
∟ Focus on: real-time indexing and trending detection  
  
► Design User Affinity  
∟ Focus on: collaborative filtering and scoring models  
  
7. Search, Messaging, and Delivery Systems  
  
► Design a Word Dictionary  
∟ Focus on: trie implementation and prefix lookups  
  
► Design Text-Based Search Engine  
∟ Focus on: tokenization, inverted index, and ranking  
  
► Design SQL-backed Message Broker  
∟ Focus on: durability, ordering, and delivery semantics  
  
► Design a Distributed Task Scheduler  
∟ Focus on: idempotency, retries, and time-based triggering  
  
► Design a service to show Recent Searches  
∟ Focus on: LRU cache strategies and user-level isolation  
  
8. Streaming, Sync, and Media  
  
► Design a Video Processing Pipeline  
∟ Focus on: batch vs realtime processing and encoding layers  
  
► Design Airline Check-in  
∟ Focus on: concurrency control, seat allocation, and time-based locking  
  
► Design a Remote File Sync Service  
∟ Focus on: delta sync, conflict detection, and version control  
  
► Design a Text-based Cricket Commentary Service  
∟ Focus on: event streaming + real-time fan engagement  
  
► Design “Who’s Near Me” Service  
∟ Focus on: location sharding + frequent geo updates






**Afternoon (3 hrs): Fleet automation**  
5. **Device management:** NETCONF/YANG, gNMI and streaming telemetry vs. SNMP, and ZTP.  
6. **Safe deployments:** canary, then fault domain, then region; pre- and post-checks; drain → upgrade → undrain; auto-halt and rollback.  
7. **Access control:** TACACS+/AAA, certificate-based SSH, break-glass access, and audit logs.  
8. **Monitoring:** interface errors, optics, BGP flaps, gray failures, and sFlow.

**Evening (2 hrs): Principal-level signal**  
9. **Practice one system design out loud:** "Safely patch 50K network devices across regions." Cover source of truth, orchestration, concurrency limits, health gates, rollback, and observability.  
10. **Prepare 2–3 STAR stories:** an outage you handled, automation that cut toil (with numbers), and a cross-team influence example.  
11. **Read about OCI networking:** VCN, regions, availability domains, and fault domains, enough to speak its language.

**Skip for now:** deep protocol internals like OSPF/IS-IS details, QoS minutiae, and vendor CLI syntax.