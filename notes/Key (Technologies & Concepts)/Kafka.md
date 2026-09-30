#### Links

https://learn.conduktor.io/kafka/what-is-apache-kafka/

https://developer.confluent.io/courses/apache-kafka/events/

https://www.educative.io/courses/system-design-deep-dive-real-world-distributed-systems/kafka-deep-dive-for-system-design

https://www.educative.io/courses/scalable-data-pipelines-kafka/introduction

#### What is Kafka ?

Apache Kafka is a fast, scalable, fault-tolerant, publish-subscribe messaging system.

##### ✅ Kafka vs. Other Messaging Systems

| Feature                 | **Kafka**                                               | **Traditional MQs** (e.g., RabbitMQ, ActiveMQ)         |
| ----------------------- | ------------------------------------------------------- | ------------------------------------------------------ |
| **Storage Model**       | Log-based: messages stored on disk and replayable       | Queue-based: messages deleted once consumed            |
| **Throughput**          | Very high                                               | Moderate to high                                       |
| **Durability & Replay** | Strong: can replay from any offset                      | Typically once-and-done delivery                       |
| **Message Ordering**    | Guaranteed within a **partition**                       | Guaranteed per queue (but can vary by config)          |
| **Consumer Model**      | Pull-based (polling by consumers)                       | Push-based (broker sends messages to consumers)        |
| **Scalability**         | Easily scales with partitions/brokers                   | Scaling can get complex (connections, routing, queues) |
| **Use Case Fit**        | Event streaming, big data pipelines                     | Task queues, transactional messaging, RPC              |
| **Dependencies**        | Needs Zookeeper (or KRaft in newer versions)            | Fewer dependencies                                     |
| **Delivery Guarantees** | At least once, at most once, exactly once (with effort) | Usually at most once or at least once                  |

#### What is a Topic & Partition?

A **Topic** in Kafka is like a **named channel or category** where messages (events) are written to and read from.
Basically, how Kafka stores and organizes messages across its system and essentially a collection of messages are Topics. In addition, we can replicate and partition Topics. Here, replicate refers to copies and partition refers to the division. Also, visualize them as logs wherein, Kafka stores messages. However, this ability to replicate and partitioning topics is one of the factors that enable Kafka’s fault tolerance and scalability.
![](https://miro.medium.com/v2/resize:fit:875/0*q12oWi99udQuOF-e.png)

In a Kafka cluster, Topics are split into Partitions and also replicated across brokers.

1. However, to which partition a published message will be written, there is no guarantee about that.
2. Also, we can add a key to a message. Basically, we will get ensured that all these messages (with the same key) will end up in the same partition if a producer publishes a message with a key. Due to this feature, Kafka offers message sequencing guarantee. Though, unless a key is added to it, data is written to partitions randomly.
3. Moreover, in one partition, messages are stored in the sequenced fashion.
4. In a partition, each message is assigned an incremental id, also called offset.
5. However, only within the partition, these offsets are meaningful. Moreover, in a topic, it does not have any value across partitions.
6. There can be any number of Partitions (There is a limit of 2Mil partitions)

There are few partitions in every Kafka broker. Moreover, each partition can be either a leader or a replica of a topic. In addition, along with updating of replicas with new data, Leader is responsible for all writes and reads to a topic. The replica takes over as the new leader if somehow the leader fails.

![](https://miro.medium.com/v2/resize:fit:875/0*KmRW_N6hf_emvxGz.png)

#### What is a Broker?

A single Kafka server is called a Kafka Broker. That Kafka broker is a program that runs on the Java Virtual Machine and usually a server that is meant to be a Kafka broker will solely run the necessary program and nothing else.

![Kafka Broker](https://www.scaler.com/topics/images/kafka-broker.webp) As seen in the illustration above, the Kafka broker is used for managing the storage of the data records/messages in the topic. It can be simply understood as the mediator between the two. We define a Kafka cluster when there is more than one broker present. The Kafka broker is responsible for transferring the conversation that the publisher is pushing in the Kafka log commit and the subscriber shall be consuming these messages. The conversation is mediated between the multiple systems, enabling the delivery of the data records/ message to process to the right consumer.

While the Kafka cluster consists of various brokers. Kafka cluster implements the Zookeeper for maintaining the state of the cluster. It has also been seen that an individual broker can handle thousands of requests for reads and writes per second. When no performance impact is seen then every broker in the Kafka cluster can handle terabytes of messages. ZooKeeper also performs the broker leader election.

Kafka Broker is structured as a KafkaServer, that is hosting various topics. The stated topics must be partitioned across the various brokers spread in a Kafka cluster. The single broker is hosting the topic partitions for more than one topic, while a topic is partitioned for a single partition. Also, the Kafka producers push a message to a broker. Then the broker receives the data record and stores it. This stored data remains over the disk defined by a distinct offset. However, the partition, topic, and offset of a broker allow consumers to fetch messages.

Hence, the brokers could create the Kafka cluster by exchanging information with each other either directly or indirectly via the Zookeeper. It is one broker among all the brokers in a Kafka cluster that acts as the controller.

#### What is Kafka Cluster?

As we already studied the Kafka broker acts as a mediator between the systems to smoothly transfer the message from the source to the destination.

When there is a band containing more than one broker working together that is termed the Kafka Cluster. The number of brokers in a Kafka Cluster contain can range from one or can even potentially contain hundreds of brokers. Organizations dealing with streaming data such as Netflix, Hotstar+, Ola, and Uber contain thousands of Kafka brokers for effectively managing and handling the data.

Now, you must be thinking about how can you identify a specific broker out of the range of brokers that a Kafka Cluster might contain. Well, any specific Kafka broker residing in a Kafka cluster could be recognized via its unique numeric ID.

Below is a crisp illustration of how a Kafka Cluster is made by five Kafka brokers.

![five Kafka brokers](https://www.scaler.com/topics/images/five-kafka-brokers.webp)

The Kafka cluster consists of multiple components, widely known as nodes, that together comprise the Kafka cluster. Various Kafka services such as Kafka Broker, Kafka Consumer, Kafka Producer, Zookeeper, etc are widely deployed to form a complete Kafka Cluster. Multiple functionalities like a failure, replication, data high availability, multiple partition support, etc are supported by it.

With these various brokers in a Kafka Cluster, the message is distributed over various instances. The Zookeeper plays a crucial role as a part of the Kafka cluster. It helps to synchronize, manage as well as handle the entire distributed configuration. Zookeeper also acts as the coordinator interface for the multiple Kafka brokers and consumers.

While the Kafka producer shall be pushing the message into the Kafka cluster, from where the message reaches the end of the Kafka cluster, where it could be easily read or consumed by the Kafka consumers.

You can understand more about it in the [Kafka Cluster](https://www.scaler.com/topics/kafka-cluster/) link, where we shall deep dive into the Kafka Cluster architecture as well.


#### What is Replication in Kafka Cluster?

In Kafka, replication happens at the partition granularity i.e. copies of the partition are maintained at multiple broker instances using the partition’s write-ahead log.

> Every partition in a topic has a write-ahead log where all the messages for that partition are stored in order. The messages are identified by the unique offset.

**Replication factor** defines the number of copies of the partition that needs to be kept.

![](https://miro.medium.com/v2/resize:fit:776/1*Sza0NeMLKrNFNC245mLzWw.jpeg)

A Kafka cluster with Replication Factor 2

> A replication factor of 2 means that there will be two copies for every partition.

**Leader for a partition:** For every partition, there is a replica that is designated as the **leader.** The Leader is responsible for sending as well as receiving data for that partition. All the other replicas are called the **in-sync replicas** (or followers) of the partition.

In-sync replicas are the subset of all the replicas for a partition having same messages as the leader.

> Producers can choose to receive acknowledgements for the data writes to the partition using the “acks” setting**.**

There are 3 levels of acknowledgements that Producers can choose from depending upon their use case of Kafka.

![](https://miro.medium.com/v2/resize:fit:663/1*aKWxdQSKe9648Ejq74Olsw.png)

The value of ==acks== varies from application to application. For an application where high durability needs (example in case of transaction data), acks all is recommended whereas in cases where lower latency is more important _acks = 0_ is used (example — user’s location data).

> **Note:** Acks parameter defines the number of acknowledgements that should be waited for from the in-sync replicas only.

##### How does Replication Work?

In Kafka, the following 2 conditions need to be met for a node to be considered alive.

- The node must maintain its session with zookeeper
- If the node is a follower, it must not be “_far behind”_ the leader

Naturally, you must be asking how does a leader determine if a follower is caught up or not?

The answer is fairly simple — leader maintains a list of its followers and tracks their status. If a follower dies or is not able to replicate and falls behind the leader, it gets removed from the in-sync replica list.

Kafka gives us the power to decide the condition for which a replica is considered stuck or falling behind.

- _replica.lag.max.messages  
    _This parameter decides the allowed difference between replica’s offset and leader’s offset. if the difference becomes more than (replica.lag.max.messages-_1 )_ then that replica is considered to be lagging behind and is removed from the in-sync replica list.  
    If the value of replica.lag.max.messages is n, it means that as long as the follower is behind the leader by not more than n-1 messages, it won’t be removed from the in-sync replica list.
- _replica.lag.time.max.ms  
    _This parameter defines the maximum time interval within which every follower must request the leader for its log. If for some reason a replica is unable to do so, it will be removed from the in-sync replica list.

> Kafka guarantees that if a message has been acknowledged as committed, then even in case of leader failure, messages won’t get lost.

##### Failure Recovery

Now that we know the basics of replication in Kafka, lets discuss how Kafka behaves in case of failures. We already know that — In Kafka, both reads and writes occur at leader. So, what happens in case Leader goes down? Well, a new leader needs to be chosen from the remaining replicas.

Only the replicas that are part of in-sync replica list are eligible for becoming the leader and the list of in-sync replicas is persisted to zookeeper whenever any changes are made to it
. Also, Kafka’s guarantee of no data loss is applicable only if there exists an in-sync replica. In case of no such replica, this guarantee is not applicable.

Although, Its highly uncommon for all the replicas of a partition to go down. But in case this happens — there are 2 behaviors that can be implemented in Kafka —

- Wait for an in-sync replica to come up — in this case we chose the replica as leader and hope that it has all the data.
- Wait for any replica to come up

The choice really boils down to choosing between availability and consistency. _By default, the first behavior is the chosen one in Kafka._
 
#### What is a Producer ?

Producers are client applications that write event messages to topics in the Kafka cluster. Messages are stored in the form of key-values in the partitions of the topics. As I mentioned in the previous article, messages produced with the same key are written to the same partition. Sending keys during production is not mandatory.

![](https://miro.medium.com/v2/resize:fit:620/1*RQgjAqWhnhbbg4OhM139lQ.png)

To produce messages from your application, you first need to write your producer configs. Some configs that may be needed for producer configuration are as follows.

- **acks:** Acks refers to the minimum number of acknowledgments that must come from the broker for the producer to accept a message as sent to the Kafka cluster. It can take the values “all”, “0” and “1”. all -> producer will wait for the leader section to receive confirmation that all followers have committed the message. 1 -> It is enough for the leader partition to write to its own commit log. 0 -> no ack expected.
- **max.in.flight.requests.per.connection:** The maximum number of unapproved requests that the client will send in a single connection before blocking. The default value is 5.
- **linger.ms:** Represents the delay time before the batch record request is ready to be sent. All records received between request transmissions are brought together in a single request by the producer. linger.ms specifies the upper limit of latency for batch processing. The default value is 0. This means there will be no delay and batches will be sent immediately (even if there is only 1 message in the batch). In some cases, the client may increase linger.ms to reduce the number of requests even under moderate load to increase throughput. Only this way more records will be stored in memory.
- **batch.size:** When more than one record is sent to the same partition, the producer tries to bring the records together. In this way, the performance of both the client and the server can be increased. batch.size represents the maximum size (in bytes) of a single batch. Small batch size will make batch processing trivial and reduce efficiency; A batch size that is too large will waste memory as a buffer is usually allocated to wait for extra records.

You can access all configs and details from the [confluent document](https://docs.confluent.io/platform/current/installation/configuration/producer-configs.html).

The producer needs serializers when producing messages to Kafka. There are various serializers but generally string, json and Avro serializer are used.

##### Kafka Message Anatomy

[](https://learn.conduktor.io/kafka/kafka-producers/#Kafka-Message-Anatomy-2)

Kafka messages are created by the producer. A Kafka message consists of the following elements:

![Diagram showing how Kafka Producers structure a message created by the Apache Kafka Producer.](https://learn.conduktor.io/kafka/_next/image/?url=https%3A%2F%2Fimages.ctfassets.net%2Fo12xgu4mepom%2F2TuJ55uK20OUVLQgZ17yUU%2F9bb611597f4914e971d85e3938856968%2FKafka_Producers_3.png&w=3840&q=75 "Kafka Producers - The structure of a Kafka Message")

Structure of a Kafka Message

- **Key**. Key is optional in the Kafka message and it can be null. A key may be a string, number, or any object and then the key is serialized into binary format.
    
- **Value**. The value represents the content of the message and can also be null. The value format is arbitrary and is then also serialized into binary format.
    
- **Compression Type**. Kafka messages may be compressed. The compression type can be specified as part of the message. Options are `none`, `gzip`, `lz4`, `snappy`, and `zstd`
    
- **Headers**. There can be a list of optional Kafka message headers in the form of key-value pairs. It is common to add headers to specify metadata about the message, especially for tracing.
    
- **Partition + Offset**. Once a message is sent into a Kafka topic, it receives a partition number and an offset id. The combination of topic+partition+offset uniquely identifies the message
    
- **Timestamp**. A timestamp is added either by the user or the system in the message.


#### What is a Consumer ?

[](https://learn.conduktor.io/kafka/kafka-consumers/#Kafka-Consumers-0)

Applications that read data from Kafka topics are known as consumers. Applications integrate a Kafka client library to read from Apache Kafka. Excellent client libraries exist for almost [all programming languages](https://learn.conduktor.io/kafka/kafka-sdk-list/) that are popular today including Python, Java, Go, and others.

Consumers can read from one or more partitions at a time in Apache Kafka, and data is read in order **within each partition** as shown below.

![Kafka consumers in this diagram are reading messages from various Apache Kafka Brokers and Topics.](https://learn.conduktor.io/kafka/_next/image/?url=https%3A%2F%2Fimages.ctfassets.net%2Fo12xgu4mepom%2F2rngiBRe1Db4fOU9vJjD92%2F919d7900945ad93db62e1d6b37699ad7%2FKafka_Consumers_1.png&w=3840&q=75 "Kafka Consumers")

Kafka Consumers

A consumer always reads data from a lower offset to a higher offset and cannot read data backwards (due to how Apache Kafka and clients are implemented).

If the consumer consumes data from more than one partition, the message order is not guaranteed across multiple partitions because they are consumed simultaneously, but the message read order is still guaranteed within each individual partition.

By default, Kafka consumers will only consume data that was produced after it first connected to Kafka. Which means that to read historical data in Kafka, one must specify it as an input to the command, as we will see in the practice section.

Kafka consumers are also known to implement a "pull model". This means that Kafka consumers must request data from Kafka brokers in order to get it (instead of having Kafka brokers continuously push data to consumers). This implementation was made so that consumers can control the speed at which the topics are being consumed.

##### Kafka Message Deserializers

[](https://learn.conduktor.io/kafka/kafka-consumers/#Kafka-Message-Deserializers-1)

Serialization & Deserialization

Data being consumed must be deserialized in the same format it was serialized in.

As we have seen before, the data sent by the Kafka producers is [serialized](https://learn.conduktor.io/kafka/kafka-producers/). This means that the data received by the Kafka consumers must be correctly deserialized in order to be useful within your application. Data being consumed must be deserialized in the same format it was serialized in. For example:

- if the producer serialized a `String` using `StringSerializer`, the consumer must deserialize it using `StringDeserializer`
    
- if the producer serialized an `Integer` using `IntegerSerializer`, the consumer must deserialize it using `IntegerDeserializer`
![Kafka Consumers must use the same format for deserialization that was used by the producer when serializing the message. This daigram shows the deserialization process.](https://learn.conduktor.io/kafka/_next/image/?url=https%3A%2F%2Fimages.ctfassets.net%2Fo12xgu4mepom%2F6ZTT5N2bkfebM8gfWTLvCg%2F11ca8bd5ab3203d48d5b1494531cbc6b%2FKafka_Consumers_2.png&w=3840&q=75 "Apache Kafka Consumers and Message Deserializatrion")

Deserialization

The serialization and deserialization format of a topic must not change during a topic lifecycle. If you intend to switch a topic data format (for example from JSON to Avro), it is considered best practice to create a new topic and migrate your applications to leverage that new topic.

Poison Pills

Messages sent to a Kafka topic that do not respect the agreed-upon serialization format are called poison pills. [They are not fun to deal with.](https://www.slideshare.net/ConfluentInc/streaming-apps-and-poison-pills-handle-the-unexpected-with-kafka-streams-loic-divad-xebia-france-kafka-summit-sf-2019)

Failure to correctly deserialize may cause crashes or inconsistent data being fed to the downstream processing applications. This can be tough to debug, so it is best to think about it as you're writing your code the first time.

##### Kafka Consumer Groups & Offsets

###### Kafka Consumer Groups

Consumers that are part of the same application and therefore performing the same "logical job" can be grouped together as a Kafka consumer group.
A topic usually consists of many partitions. These partitions are a unit of parallelism for Kafka consumers.
The benefit of leveraging a Kafka consumer group is that the consumers within the group will coordinate to split the work of reading from different partitions.

![Apache Kafka Consumer Group diagram showing how a consumer group reads messages from a Kafka topic with 5 partitions.](https://learn.conduktor.io/kafka/_next/image/?url=https%3A%2F%2Fimages.ctfassets.net%2Fo12xgu4mepom%2F4AFnzk6VE1HSnnJN1MGftu%2F76288b1d9df5a5ae281c4accf5f25ec1%2FConsumer_Group_reading_from_topic_with_5_partitions.png&w=3840&q=75 "Kafka Consumer Group reading from topic with 5 partitions")

###### Kafka Consumer Group ID

[](https://learn.conduktor.io/kafka/kafka-consumer-groups-and-consumer-offsets/#Kafka-Consumer-Group-ID-1)

In order for indicating to Kafka consumers that they are part of the same specific group , we must specify the consumer-side setting `group.id`.

Kafka Consumers automatically use a `GroupCoordinator` and a `ConsumerCoordinator` to assign consumers to a partition and ensure the load balancing is achieved across all consumers in the same group.

It is important to note that each topic partition is only assigned to one consumer within a consumer group, but a consumer from a consumer group can be assigned multiple partitions.

![Apache Kafka Consumer Group diagram showing how a consumer group reads messages from a Kafka topic with 5 partitions.](https://learn.conduktor.io/kafka/_next/image/?url=https%3A%2F%2Fimages.ctfassets.net%2Fo12xgu4mepom%2F4AFnzk6VE1HSnnJN1MGftu%2F76288b1d9df5a5ae281c4accf5f25ec1%2FConsumer_Group_reading_from_topic_with_5_partitions.png&w=3840&q=75 "Kafka Consumer Group reading from topic with 5 partitions")

In the example above, _Consumer 1_ of consumer group _consumer-group-application-1_ has been assigned _Partition 0_ and _Partition 1_, whereas _Consumer 2_ is assigned _Partition 2_ and _Partition 3_, and finally _Consumer 3_ is assigned _Partition 4_. Only _Consumer 1_ receives messages from _Partition 0_ and _Partition 1_, while only consumer _Consumer 2_ receives messages from _Partition 2 and 3,_ and only _Consumer 3_ receives messages from _Partition 4_.

Each of your applications (that may be composed of many consumers) reading from Kafka topics must specify a different `group.id`. That means that multiple applications (consumer groups) can consume from the same topic at the same time:

![Diagram showing consumers within a consumer group reading messages from different topic partitions.](https://learn.conduktor.io/kafka/_next/image/?url=https%3A%2F%2Fimages.ctfassets.net%2Fo12xgu4mepom%2F2FCDtp8CP4DLE0m3SMnqNn%2Fa7414ad78bf8c61c57ea6de4bb314709%2FKafka_Consumer_Groups_1.png&w=3840&q=75 "Kafka Consumer Groups & Topics")

Kafka Consumer Groups

If there are more consumers than the number of partitions of a topic, then some consumers will remain inactive as shown below. Usually, we have as many consumers in a consumer group as the number of partitions. If we want more consumers for higher throughput, we should create more partitions while creating the topic. Otherwise, some of the consumers may remain inactive.

![Diagram shows Consumer in a Kafka Consumer Group inactive when there are more consumers than partitions.](https://learn.conduktor.io/kafka/_next/image/?url=https%3A%2F%2Fimages.ctfassets.net%2Fo12xgu4mepom%2FWam1FbUec10Blz1V3YttV%2F7e08aa72e3b75cec686cec850e2ce51b%2FKafka_Consumer_Groups_2.png&w=3840&q=75 "Kafka Consumer Groups & Consumer Inactivity")

More consumers than partitions

---

###### Kafka Consumer Offsets

[](https://learn.conduktor.io/kafka/kafka-consumer-groups-and-consumer-offsets/#Kafka-Consumer-Offsets-2)

Kafka brokers use an internal topic named `__consumer_offsets` that keeps track of what messages a given **consumer group** last successfully processed.

As we know, each message in a Kafka topic has a partition ID and an offset ID attached to it.

Therefore, in order to "checkpoint" how far a consumer has been reading into a topic partition, the consumer will regularly **commit** the latest processed message, also known as **consumer offset**.

In the figure below, a consumer from the consumer group has consumed messages up to offset `4262`, so the consumer offset is set to `4262`.

![Diagram showing how Kafka Consumers from a Consumer Group read messages from the last committed consumer offset.](https://learn.conduktor.io/kafka/_next/image/?url=https%3A%2F%2Fimages.ctfassets.net%2Fo12xgu4mepom%2F4tMX1YrCV40XMKLZH1pHTE%2F26f0f5ba577d10da0dfff967b98fa69c%2FKafka_Consumer_Groups_3_2x.png&w=3840&q=75 "Kafka Consumer Groups & Committed Offsets")

Consumer Offset

Most client libraries automatically commit offsets to Kafka for you on a periodic basis, and the responsible Kafka broker will ensure writing to the `__consumer_offsets` topic (therefore consumers do not write to that topic directly).

The process of committing offsets is not done for every message consumed (because this would be inefficient), and instead is a periodic process.

This also means that when a specific offset is committed, all previous messages that have a lower offset are also considered to be committed.

---

###### Why use Consumer Offsets?

[](https://learn.conduktor.io/kafka/kafka-consumer-groups-and-consumer-offsets/#Why-use-Consumer-Offsets?-3)

Offsets are critical for many applications. If a Kafka client crashes, a rebalance occurs and the latest committed offset help the remaining Kafka consumers know where to restart reading and processing messages.

In case a new consumer is added to a group, another consumer group rebalance happens and consumer offsets are yet again leveraged to notify consumers where to start reading data from.

Therefore consumer offsets must be committed regularly.


#### What are the Delivery Guarantees that Kafka offers?

A consumer reading from a Kafka partition may choose when to commit offsets. That strategy impacts the behaviors if messages are skipped or read twice upon a consumer restart. These behaviors are discussed on this page.

##### At Most Once Delivery

[](https://learn.conduktor.io/kafka/delivery-semantics-for-kafka-consumers/#At-Most-Once-Delivery-0)

In this case, offsets are committed as soon as a message batch is received after calling `poll()`. If the subsequent processing fails, the message will be lost. It will not be read again as the offsets of those messages have been committed already. This may be suitable for systems that can afford to lose data.

The sequence of steps is illustrated below.

![Diagram of Kafka Consumer Delivery Semantics set to At Most Once](https://learn.conduktor.io/kafka/_next/image/?url=https%3A%2F%2Fimages.ctfassets.net%2Fo12xgu4mepom%2F3HWZKKcNQLUiVaZBHOQKWt%2F422c797f8ee23205502b6ddc6e321e54%2FAdv_Delivery_Semantics_for_Consumers_1_2x.png&w=3840&q=75 "Apache Kafka Delivery Semantics for Consumers At Most Once")

At Most Once

##### At Least Once Delivery (usually preferred)

[](https://learn.conduktor.io/kafka/delivery-semantics-for-kafka-consumers/#At-Least-Once-Delivery-\(usually-preferred\)-1)

In at-least-once delivery, every event from the source system will reach its destination, but sometimes retries will cause duplicates. Here, offsets are committed after the message is processed. If the processing goes wrong, the message will be read again. This can result in duplicate processing of messages. This is suitable for consumers that cannot afford any data loss.

Idempotent Processing

Make sure your processing is idempotent (i.e. processing again the messages won’t impact your systems)

![Diagram showing Kafka Consumer Delivery Semantics set to At Least Once](https://learn.conduktor.io/kafka/_next/image/?url=https%3A%2F%2Fimages.ctfassets.net%2Fo12xgu4mepom%2F3X2ZPpl4wkTmvnly4e60cu%2F6d3b7404b93030bdfff9012f465ac560%2FAdv_Delivery_Semantics_for_Consumers_2_2x.png&w=3840&q=75 "Apache Kafka Delivery Semantics for Consumers At Least Once")

##### Exactly Once Delivery

[](https://learn.conduktor.io/kafka/delivery-semantics-for-kafka-consumers/#Exactly-Once-Delivery-2)

Some applications require not just at-least-once semantics (meaning no data loss), but also exactly-once semantics. Each message is delivered exactly once. This may be achieved in certain situations if Kafka and the consumer application cooperate to make exactly-once semantics happen.

- This can only be achieved for Kafka topic to Kafka topic workflows using the transactions API. The Kafka Streams API simplifies the usage of that API and enables exactly once using the setting `processing.guarantee=exactly.once`.
- For Kafka topic to External System workflows, to _effectively_ achieve exactly once, you must use an idempotent consumer.
#### Detailed Design

###### Partition Implementation

- **Kafka partitions are not implemented as a single large file**.
    - Instead, each partition is implemented as a **logical log**, divided into **multiple segment files**
- **Segment files**:
    - Are **append-only** files that store messages in order.
    - Typically have a **configurable maximum size** (e.g., 1 GB) or a **maximum retention time** (e.g., 1 week).
    - Make it easy to delete old data by removing entire segment files, rather than deleting parts of a single large file.
    - Kafka does **not buffer data in user-space memory and then write** to disk.
    - Instead, Kafka uses **memory-mapped I/O (mmap)** to write to segment files.
    - **Segment files exist on disk immediately**, but new data may reside in the OS's **page cache** and not be physically persisted yet.
- **Active Segment**:
    - The **last segment** in the partition is considered the **active segment**, where new messages are appended.
    - Once the segment reaches its size/time threshold, a **new segment is created**, and the old one is closed.
- **Flushing to disk**:
    - Kafka uses **OS-level page caching** for performance.
    - Data is flushed to disk either:
        - When a **configured size limit** is reached,
        - Or a **configured time limit** expires,
        - Or explicitly by the broker (via `log.flush.interval.messages/time` settings).
- **Data retention and deletion**:
    - Kafka brokers **do not keep data forever**.
    - Old segment files are deleted based on:
        - **Time-based retention** (e.g., retain logs for 7 days), or
        - **Size-based retention** (e.g., keep up to 100 GB per topic-partition).
    - This enables **efficient log compaction or cleanup** without needing to edit or rewrite existing files.
- **Message availability to consumers**:
    - Messages become available to consumers **after being flushed to disk**, although many consumers can read from the page cache before that happens in practice.

###### Message IDs or offsets[](https://www.educative.io/courses/system-design-deep-dive-real-world-distributed-systems/efficiency-of-kafka#Message-IDs-or-offsets)

- Kafka **does not assign unique message IDs** like other messaging systems.
- Instead, it uses a **logical offset** to identify each message within a **partition**.
- A **partition** is stored as a **single log file** (or multiple segment files).
- Each message in a partition has a **sequential offset**, assigned as it’s appended.
- Offsets **increase over time**, but the gap between offsets may **vary** due to **variable message sizes**.
- This design **eliminates the need for random access data structures**, simplifying storage and lookup.
- To assign a new message an offset, Kafka **adds the message's length** to the **last offset**.
###### Offset Storage

Kafka has **two main models** (depending on version/configuration):

✅ **A. In Modern Kafka (Kafka 0.9+ with Kafka-based Consumer Groups)**
- Offsets are stored in a **special internal topic**: `__consumer_offsets`.
- This topic is **managed by Kafka itself**, allowing:
    - Better scalability.
    - Broker-side coordination.
    - Elimination of ZooKeeper dependence for offset tracking.
🧾 Example:
- Each consumer group stores its offset per topic-partition in `__consumer_offsets`.
✅ **B. In Older Kafka (pre-0.9 or with legacy consumers)**
- Offsets were stored in **ZooKeeper**.
- This required additional overhead and tight coupling between consumers and ZooKeeper.
- It’s now considered **deprecated and discouraged**.
#### Q & A

##### Instead of zookeeper what does Kafka use these days to manage the brokers in cluster?

As of **Kafka 2.8.0+ (April 2021)** and **fully production-ready in Kafka 3.3.0+ (September 2022)**, **Kafka can run without ZooKeeper** using a new mode called:

✅ **KRaft (Kafka Raft Metadata Mode)**

---

🚫 What was the role of ZooKeeper before?

ZooKeeper was used for:
- Managing **broker metadata** (cluster membership, topics, configs).
- Electing the **controller broker**.
- Storing partition leadership info and configuration.
---

✅ What replaces it now?

Kafka now uses **KRaft** — a built-in **Raft-based consensus protocol** — to manage cluster metadata **internally**, eliminating the need for ZooKeeper.

---
🧠 How KRaft Works:

- A subset of brokers are designated as **KRaft controllers**.
- These controllers form a **Raft quorum** to manage:
    - Broker registrations
    - Topic metadata
    - Controller elections
    - Configuration changes
- Metadata is written to a special **internal topic** (`__cluster_metadata`) and replicated like any other topic.
---

🔁 Transition Timeline

| Kafka Version  | KRaft Support                             |
| -------------- | ----------------------------------------- |
| 2.8.0          | Early (preview)                           |
| 3.0.x – 3.2.x  | More stable, still not production-ready   |
| **3.3.0+**     | ✅ **Production-ready KRaft**              |
| 3.5.x+         | KRaft strongly recommended                |
| 4.0 (expected) | 📦 **ZooKeeper will be removed entirely** |

---
🔧 How to Use KRaft Mode:

To run Kafka in **KRaft (no ZooKeeper) mode**, you:
- Use the `kafka-storage.sh` tool to format storage directories.
- Start Kafka brokers with a KRaft-specific config (no `zookeeper.connect`).
- Define controllers using `process.roles=controller,broker` or `process.roles=controller`.
---
✅ Benefits of KRaft:
- Simpler architecture (no external dependency like ZooKeeper).
- Faster controller failover.
- Stronger metadata consistency via Raft.
- Easier deployment, ops, and security.
---
Summary:

Kafka now uses **KRaft (Kafka Raft)** to manage cluster metadata **instead of ZooKeeper**.  
This is the **future** of Kafka — and by version 4.0, ZooKeeper will be **fully retired**.
Let me know if you want a sample `server.properties` for KRaft mode.

##### Does KRaft run on each broker?

It **depends on how your Kafka cluster is configured**:

🟢 If you’re running a **KRaft-based cluster (no ZooKeeper)**:
- **Some brokers are configured as KRaft controllers**
- Others are **regular brokers**
- KRaft itself (the Raft quorum) **only runs on the controller nodes**

`Cluster Nodes:
- Broker 1 → controller.quorum.voters
- Broker 2 → controller.quorum.voters
- Broker 3 → controller.quorum.voters
- Broker 4 → regular broker`

> ✅ Only the controller nodes run the **KRaft consensus logic**.
##### Can a consumer from same consumer group read from multiple partitions

Yes, **a consumer in the same consumer group _can_ read from multiple partitions**, but only **if the number of partitions assigned to the group exceeds the number of consumers in that group**.

How Kafka assigns partitions to consumers in a group:
- Kafka uses a **partition assignment strategy** to evenly distribute partitions among consumers in a group.
- **Each partition is consumed by only one consumer in a group** (i.e., no duplicate consumption).
- But **a single consumer can be assigned multiple partitions**.

Examples:
✅ Scenario 1: 3 partitions, 2 consumers in the same group
- Consumer 1 → gets 2 partitions
- Consumer 2 → gets 1 partition  
    → So yes, **Consumer 1 reads from multiple partitions**.
❌ Scenario 2: 3 partitions, 3 consumers in the same group
- Each consumer gets 1 partition  
    → No consumer reads from multiple partitions.
❌ Scenario 3: 3 partitions, 4 consumers in the same group
- Only 3 partitions to assign, 1 consumer will remain idle  
    → One or more consumers get **no data**.

Summary:
- ✔️ A consumer **can** read from multiple partitions.
- ✔️ This is **normal behavior** when there are more partitions than consumers.
- ❌ A partition **cannot** be consumed by more than one consumer **within the same group**.

##### how does a Kafka producer know which Kafka broker the topic resides and to which partition it has to push message to ?

Let’s break this down step by step to understand how a **Kafka Producer** figures out:
1. **Which broker to send the message to**
2. **Which partition within a topic to target**
---
🧩 Step 1: **Producer connects to a bootstrap broker**

When a producer starts up, it connects to one or more **bootstrap brokers** (addresses given in the config like `localhost:9092`, etc.).
- These are just entry points — not necessarily where data will go.
- The producer sends a **metadata request** to learn about:
    - All **topics**
    - Their **partitions**
    - Which broker is the **leader** for each partition
---
🧠 Step 2: **Kafka sends back metadata**

Kafka responds with metadata like:
{
  "topic": "user-events",
  "partitions": [
    {"partition": 0, "leader": broker-1},
    {"partition": 1, "leader": broker-2},
    {"partition": 2, "leader": broker-3}
  ]
}
Now the producer **knows which broker leads each partition**.

---
🎯 Step 3: **Producer chooses the partition**

The producer uses one of the following strategies:

| Scenario                    | What Happens                                                               |
| --------------------------- | -------------------------------------------------------------------------- |
| **You specify a partition** | Producer sends directly to it                                              |
| **You provide a key**       | Kafka applies a **hash function** on the key and maps it to a partition    |
| **No key and no partition** | Kafka uses **round-robin** to evenly distribute messages across partitions |

**Example:**

ProducerRecord<String, String> record = new ProducerRecord<>("user-events", "userId123", "User logged in");

Here:
- Topic: `"user-events"`
- Key: `"userId123"`
- Value: `"User logged in"`

Kafka hashes `"userId123"` and assigns the record to the appropriate partition.

---
🚚 Step 4: **Producer sends message to the partition leader broker**
Once partition is decided:
- Producer sends the message to the **leader broker** of that partition
- The broker **writes it to the log**
- If replication is enabled, the leader propagates the data to its **follower replicas**

---
🔄 What if metadata changes?
Kafka producers **periodically refresh metadata**:
- If a broker goes down or a new one becomes leader
- If a new partition is added
- If there's an error like "unknown topic or partition"
---
✅ Summary

|Question|Answer|
|---|---|
|**How does producer know where to send?**|Via metadata from bootstrap broker|
|**How is partition chosen?**|Specified by you, or based on key (hash), or round-robin|
|**How does it know which broker?**|Metadata includes **partition-to-leader** mapping|
|**Can it adapt to changes?**|Yes, producers auto-refresh metadata|

##### If Kafka uses a pull-based model, how is it implemented at the client side, if we are using a Java client ?

Great question! Yes, **Kafka uses a pull-based model**, and when you're using the **Java client**, this means the **consumer explicitly pulls data** from the Kafka broker at its own pace.

Let’s break this down with a clear explanation and Java code example 👇

✅ What does "pull-based" mean in Kafka?
In a **pull-based system**, the **consumer** is responsible for:
- Asking for new data
- Polling the broker at regular intervals
This is in contrast to push-based systems where messages are pushed to consumers automatically.

🧩 Kafka's Pull Model in Java
The key method here is:
	consumer.poll(Duration.ofMillis(100));
This line tells Kafka:
> "Hey broker, do you have any messages for the topics I'm subscribed to? I'll wait up to 100 milliseconds."
> "

---
🔧 Java Kafka Consumer Example

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

public class MyKafkaConsumer {

    public static void main(String[] args) {
        // Kafka consumer config
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "my-consumer-group");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        props.put("auto.offset.reset", "earliest"); // or "latest"

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // Subscribe to a topic
        consumer.subscribe(Collections.singletonList("my-topic"));

        while (true) {
            // This is the pull operation
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("Received message: key=%s, value=%s, offset=%d%n",
                        record.key(), record.value(), record.offset());
            }

            // Optionally, commit offsets
            consumer.commitSync();
        }
    }
}
🔄 What happens behind the scenes?
- `poll()` sends a request to the broker
- Kafka returns a batch of messages (if available)
- Java client processes the records
- It manages offsets (manually or automatically)

---
⚙️ Benefits of pull model
- **Backpressure handling**: Consumers can control their own pace
- **Batch processing**: More efficient than one-at-a-time delivery
- **Flexible polling frequency**: You can control latency vs throughput

🧠 Summary

| Concept            | Details                                   |
| ------------------ | ----------------------------------------- |
| **Pull model**     | Consumer initiates message fetch          |
| **Java API**       | Use `poll(Duration)` to fetch messages    |
| **Why it matters** | Gives consumers control, reduces overload |


##### How do produce and pull requests know where (that is, which broker) to send the request?
Producers and consumers send a metadata request to the brokers, including a list of topics of interest. The brokers give back a response, including partitions in the topics, replica partitions, and leader partitions. This request can be sent to any broker because they all have cached metadata. The producers and consumers sending this request also cache this metadata to avoid sending requests to the wrong brokers and direct future requests correctly.