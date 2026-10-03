## Ways to Handle Distributed Transactions

Handling **distributed transactions** in a microservices architecture is challenging because microservices are designed to be loosely coupled and independent, whereas transactions typically require strong consistency and coordination across multiple services. Here are the common approaches to handle distributed transactions:

### Links
https://www.youtube.com/watch?v=d2z78guUR4g

### 1. **Two-Phase Commit (2PC)**

- **How it works**:
    - A coordinator (transaction manager) coordinates the transaction across multiple services.
    - **Phase 1 (Prepare)**: Each participant (microservice) prepares to commit and responds with a "Yes" or "No."
    - **Phase 2 (Commit/Rollback)**: If all participants agree, the coordinator sends a commit request; otherwise, it sends a rollback request.
- **Pros**:
    - Ensures atomicity (all or nothing) across services.
- **Cons**:
    - High latency due to coordination.
    - Single point of failure (coordinator).
    - Not scalable for large systems.
- **Use Case**: Suitable for systems where strong consistency is critical, such as financial systems.

### 2. **Three-Phase Commit (3PC)**

- **How it works**:
    - Extends 2PC with an additional **PreCommit Phase** to ensure non-blocking behavior.
    - **Phase 1 (CanCommit)**: Coordinator asks participants if they can commit.
    - **Phase 2 (PreCommit)**: If all participants agree, the coordinator sends a **PreCommit** message.
    - **Phase 3 (DoCommit)**: Coordinator sends a **DoCommit** message to finalize the transaction.
- **Pros**:
    - Non-blocking (participants can decide independently if the coordinator fails).
    - More fault-tolerant than 2PC.
- **Cons**:
    - Higher latency and complexity compared to 2PC.
    - Not widely adopted in practice.
- **Use Case**: Suitable for systems requiring strong consistency and fault tolerance, where 2PC’s blocking behavior is unacceptable.

### 3. **Saga Pattern**

- **How it works**:
    - A long-running transaction is broken into a sequence of local transactions, each executed by a microservice.
    - **Choreography**: Each microservice publishes events after completing its local transaction, and other services react to these events.
    - **Orchestration**: A central orchestrator coordinates the sequence of local transactions.
- **Pros**:
    - Scalable and loosely coupled.
    - No single point of failure.
- **Cons**:
    - Eventual consistency (not strongly consistent).
    - Complex to implement and debug.
- **Use Case**: Suitable for business workflows like order processing, where eventual consistency is acceptable.

### 4. **Eventual Consistency with Event Sourcing**
Event Sourcing is a pattern where instead of storing an entity's current state directly in a database, you store the complete sequence of events that led to that state — and you derive current state by replaying those events.
Traditional (CRUD) approach: an `Order` table has a `status` column. Update the order → you overwrite `status`, and the fact it was ever `pending` before becoming `shipped` is gone unless you separately log it.
Event Sourcing approach: you never overwrite anything. You append immutable facts to a log:
```
OrderCreated{orderId=1, items=[...]}
OrderPaid{orderId=1, amount=500}
OrderShipped{orderId=1, carrier="X"}
```

- **How it works**:
    - Each microservice maintains its own state and publishes events to notify other services of changes.
    - Services react to these events and update their own state asynchronously.
- **Pros**:
    - Highly scalable and decoupled.
    - Provides auditability (all events are logged).
- **Cons**:
    - Eventual consistency (not strongly consistent).
    - Complex to implement and manage.
- **Use Case**: Suitable for systems where eventual consistency is acceptable, such as e-commerce platforms.

**The companion pattern: CQRS**
Event Sourcing is almost always paired with **CQRS** (Command Query Responsibility Segregation): writes append events (the source of truth), but reads go against a separate, precomputed "read model" (a regular table optimized for queries) that's kept up to date by consuming the event stream. This avoids replaying potentially thousands of events every time you just want to display an order's current status.

### 5. **Transactional Outbox Pattern**
**The Transactional Outbox Pattern** solves a very specific, easy-to-miss problem: **you can't atomically update your database AND publish a message to a broker (like Kafka) as one operation** — they're two completely separate systems, so there's no way to wrap both in a single transaction.

**The problem it solves — the "dual write" problem**
Say an order service, on creating an order, needs to (1) save the order to its database and (2) publish an `OrderCreated` event to Kafka so other services can react. The naive code:

```java
db.save(order);              // step 1
kafka.publish("OrderCreated", event);  // step 2
```

This is unsafe in both directions:
- If the app crashes _after_ step 1 but _before_ step 2 — the order exists in the DB, but no event ever went out. Other services never find out.
- If you flip the order (publish first, save second) and the DB save fails — you've told the world an order was created that doesn't actually exist.

- **How it works**:
    - Each microservice writes its local transaction and the corresponding event to a local "outbox" table in the same database.
    - A separate process reads from the outbox and publishes the events to other services.
- **Pros**:
    - Ensures atomicity of the local transaction and event publishing.
    - Decouples the transaction from event publishing.
- **Cons**:
    - Adds complexity to the database schema.
    - Requires a separate process to handle event publishing.
- **Use Case**: Suitable for systems where atomicity is critical, such as inventory management.

### 6. **Idempotency and Retries**

- **How it works**:
    - Each microservice ensures that its operations are idempotent (can be safely retried without side effects).
    - If a failure occurs, the system retries the operation until it succeeds.
- **Pros**:
    - Simple to implement.
    - Resilient to transient failures.
- **Cons**:
    - Requires careful design to ensure idempotency.
    - Eventual consistency (not strongly consistent).
- **Use Case**: Suitable for systems where retries are acceptable, such as notification systems.

### 7. **Distributed Locking**
A distributed lock is the same idea as a mutex in a single-process program — "only one actor may hold this at a time" — but enforced across multiple machines that don't share memory. Since there's no CPU-level compare-and-swap available across a network, you need an external coordinator (or protocol) everyone agrees to trust.

**Why you need one**
In a single process, threads coordinate via in-memory locks. In a distributed system, you might have:
- Multiple instances of a service, any of which could pick up a scheduled job — you want only one to actually run it.
- Multiple consumers racing to process the same resource (e.g., "only one worker should update inventory for SKU-123 at a time").
- A leader-election scenario — only one node should act as the primary/leader.

- **How it works**:
    - A distributed lock (e.g., using ZooKeeper or Redis) is used to ensure that only one service can modify a resource at a time.
- **Pros**:
    - Ensures strong consistency for critical resources.
- **Cons**:
    - High latency due to locking.
    - Risk of deadlocks.
    - Not scalable for high concurrency.
- **Use Case**: Suitable for systems where strong consistency is required for specific resources, such as leader election.

### 8. **Message Queues with Transactions**

- **How it works**:
    - Messages are sent to a message queue (e.g., Kafka, RabbitMQ) as part of a local transaction.
    - The message queue ensures that messages are delivered reliably.
- **Pros**:
    - Decouples services and ensures reliable message delivery.
- **Cons**:
    - Eventual consistency (not strongly consistent).
    - Requires careful handling of message ordering and duplicates.
- **Use Case**: Suitable for systems where asynchronous communication is acceptable, such as


### **Choosing the Right Approach**:

| **Approach**                 | **Consistency** | **Scalability** | **Complexity** | **Use Case**                            |
| ---------------------------- | --------------- | --------------- | -------------- | --------------------------------------- |
| Two-Phase Commit (2PC)       | Strong          | Low             | High           | Financial systems                       |
| Three-Phase Commit (3PC)     | Strong          | Low             | Very High      | Systems requiring non-blocking behavior |
| Saga Pattern                 | Eventual        | High            | Medium         | Order processing                        |
| Eventual Consistency         | Eventual        | High            | High           | E-commerce platforms                    |
| Compensating Transactions    | Eventual        | High            | Medium         | Booking systems                         |
| Transactional Outbox Pattern | Strong (local)  | Medium          | Medium         | Inventory management                    |
| Idempotency and Retries      | Eventual        | High            | Low            | Notification systems                    |
| Distributed Locking          | Strong          | Low             | High           | Leader election                         |
| Message Queues               | Eventual        | High            | Medium         | Asynchronous communication              |

## RESTful API design best practices

### General RESTful API Design Principles
- Use **nouns** (resources) in URLs, not verbs.
- Keep URLs **simple, intuitive**, and **predictable**.
- Use **HTTP status codes** correctly to indicate success or error.
- Implement **pagination, filtering, sorting** for large data sets.
- Use **consistent**, clear naming conventions.
- Include **versioning** (e.g., `/api/v1/...`) to ensure backward compatibility.
- Return **standardized response structures** for errors and data.

### GET (Retrieve Resources)
#### Best Practices:
- **Retrieve a list**: `/resources`
- **Retrieve a single item**: `/resources/{id}`
- Use **query parameters** for filtering, sorting, pagination:
    - `/resources?filter=...&sort=...&page=...&size=...`
- **Idempotent and safe**: should not modify data.
- Return **appropriate data** with **200 OK**.
- If resource not found, return **404 Not Found**.
- Use **ETag** or **Last-Modified** headers for cache validation.
- Do **not** include side effects.

### POST (Create Resources)
#### Best Practices:
- **Create a new resource**: `POST /resources`
- Include **resource data** in the request body (usually JSON).
- Return **201 Created** on success.
- Include **Location header** with URL of new resource: `/resources/{id}`
- Validate data; return **400 Bad Request** if invalid.
- Do **not** include IDs in the payload unless explicitly needed.
- POST can also be used for actions, but resources should still follow REST conventions if possible.

### PUT (Update/Replace Resources)
#### Best Practices:
- **Replace an existing resource**: `PUT /resources/{id}`
- Send **full resource data** in the request body.
- Return:
    - **200 OK** if updated successfully, possibly with updated resource.
    - **204 No Content** if no response body needed.
- If resource doesn't exist, **create** (idempotent), or return **404** if creation not allowed.
- Validate input; return **400 Bad Request** if invalid.
- Use **idempotent**: multiple requests produce same result.

### DELETE (Remove Resources)
#### Best Practices:
- **Delete a resource**: `DELETE /resources/{id}`
- Return:
    - **204 No Content** on success with no body.
    - **404 Not Found** if resource doesn't exist.
- Make delete operation **idempotent**.
- Confirm deletion via secure mechanisms if needed.
- Avoid deleting multiple resources unless batch operation.

### Additional Best Practices:
- **Use consistent naming**: plural nouns (`/users`, `/orders`).
- **Return error details** in a consistent JSON format, e.g., `{ "error": "Invalid ID" }`.
- Use **appropriate status codes**:
    - `200 OK`, `201 Created`, `204 No Content`, `400 Bad Request`, `404 Not Found`, `500 Internal Server Error`, etc.
- Document your API, including request and response schemas.
- Handle **CORS**, **authentication**, and **authorization** properly.
- Consider **HATEOAS** principles where appropriate for discoverability.

### PUT Vs POST Vs PATCH

#### **POST**
- **Purpose:**  
    Usually used to **create** a new resource under a collection (e.g., `POST /users` creates a new user).
    
- **Idempotency:**  
    **Not idempotent** — multiple identical POST requests can create multiple resources or cause side effects.
    
- **Behavior:**
    - The server **generates** the resource's unique identifier (e.g., ID).
    - Can also be

- **Response:**
    - Usually returns **201 Created** with the URL of the created resource via `Location` header.
    - May return **200 OK** or **202 Accepted** for other cases.

- **Example:**  
    POST /orders with order data creates a new order.
    

---

#### **PUT**
- **Purpose:**  
    Used to **create** or **replace** a resource at a specific URL (e.g., `PUT /users/123`).
    
- **Idempotency:**  
    **Idempotent** — multiple identical PUT requests produce the same result without side effects beyond the first.
    
- **Behavior:**    
    - The client **specifies** the resource's URL (or ID).
    - Sends the **full** resource representation.
    - If the resource exists, it **replaces** it entirely.
    - If it doesn't exist, it **creates** it (depending on API design).

- **Response:**
    - Typically returns **200 OK** with updated resource or **204 No Content** if no response body.
- **Example:**  
    PUT /users/123 with user data overwrites or creates user with ID 123.
    
---

#### **PATCH**
- **Purpose:**  
    Used to **partially update** an existing resource. Instead of sending the complete resource (like in PUT), you send only the fields that need to be changed.
    
- **Behavior:**
    - The client provides a **partial representation** of the resource—only the attributes to modify.
    - The server **applies** these modifications to the existing resource.
    - It does **not** replace the entire resource but updates specific fields.
- **Idempotency:**
    - Generally **not guaranteed** to be idempotent, but can be designed to be.
    - Multiple identical PATCH requests will produce the same result if the modification operation is idempotent (e.g., setting a value).
- **Use Cases:**
    - Updating a single field, like changing a user's email.
    - Partially modifying nested objects.
    - Making incremental updates.

#### **Key Differences Recap**
- Use **POST** when **adding** new resources without specifying the ID.
- Use **PUT** when **creating or updating** a resource at a known URL, especially if you want to ensure the operation is **idempotent**.
- Use **PATCH** for **partial updates**; flexible, usually **not** strictly idempotent but can be designed to be.

| Aspect                 | **POST**                                                 | **PUT**                                           | **PATCH**                                               |
| ---------------------- | -------------------------------------------------------- | ------------------------------------------------- | ------------------------------------------------------- |
| **Purpose**            | Create a new resource under a collection                 | Create or replace a resource at a specific URL    | Partially update an existing resource                   |
| **Idempotent?**        | No                                                       | Yes                                               | Typically **not** idempotent, but can be designed to be |
| **Client defines URL** | No; server usually assigns resource ID                   | Yes; client specifies the exact resource URL      | Yes; client specifies the resource to patch             |
| **Request body**       | Usually partial data or action specifics                 | Full resource data (entire object)                | Partial data specifying fields to change                |
| **Operation**          | Creates a new resource, or triggers an action            | Replaces entire resource or creates if not exists | Updates parts of a resource without touching others     |
| **Response**           | 201 Created with Location header, or other success codes | 200 OK with updated resource, or 204 No Content   | 200 OK with updated resource, or 204 No Content         |

---
### Summary:
- **POST:** Create (usually without client specifying ID). Not idempotent.
- **PUT:** Fully replace or create at specific URL. Idempotent.
- **PATCH:** Partially update an existing resource. Not necessarily idempotent unless carefully designed, but often used for modifications.


## Callbacks

At its core, a **callback** is simply a function (or endpoint) you register with another component so that—once that component has finished some work—it will “call back” to you with the result (or a notification). In monolithic systems this can be as simple as passing a function pointer; in distributed systems it usually takes the form of:

- A **callback URI/webhook** that one service invokes on another.
- A **reply-to address** on a message queue (e.g. JMS `ReplyTo`, AMQP `reply_to`).
- A **correlation token** embedded in messages to link requests to responses.

Because services live in different processes—or even data centers—the “callback” is really a network call.

### Why Use Callbacks?

1. **Asynchronous Processing**  
    When a request takes a long time (minutes or hours), the caller doesn’t block waiting.
2. **Choreography over Orchestration**  
    Services can coordinate by notifying each other when their piece of work is done, without a central orchestrator.
3. **Resource Efficiency**  
    Instead of polling a service repeatedly, the callback pushes the result once—and only once—ready.
4. **Integration with Third Parties**  
    Webhooks are ubiquitous: payment gateways, CI/CD systems, CRM tools, etc., all “call back” into your API when events occur.

### Common Callback Patterns

| Pattern                  | Description                                                                                 |
| ------------------------ | ------------------------------------------------------------------------------------------- |
| **Webhook**              | Service A issues an HTTP POST to Service B’s pre‐registered URL when an event occurs.       |
| **Reply-to Queue**       | Service A sends a message to Service B, specifying a reply queue/topic for the response.    |
| **Polling (Fallback)**   | If callbacks fail or aren’t supported, the caller periodically polls the callee for status. |
| **Long-Poll / SSE / WS** | Variants of callbacks over persistent connections (Server-Sent Events, WebSockets).         |

### Anatomy of a Callback Interaction

1. **Register**  
    Caller (Client) registers its callback address/queue and includes it in the request.
2. **Process**  
    Target service (Server) starts work asynchronously.
3. **Invoke**  
    On completion (or failure), Server issues an HTTP call or enqueues a message to the callback address.
4. **Correlate**  
    Client uses a **correlation ID** (often a UUID) included in the original request and returned in the callback to match responses to in-flight requests.

### Use Cases

- **Payment Processing**: Your service says “charge $100” and the payment gateway later callbacks your `/webhook/payments` endpoint with success/failure.
- **Media Transcoding**: Upload a video → transcoder service processes → calls you back with URLs to the encoded streams.
- **SAGA Orchestration (Choreography Style)**: Each microservice publishes completion events, and the next service’s callback is triggered by subscribing to those events.
- **IoT / Device Management**: You send a command to a device broker; the device reports state changes via callbacks.

### Design Considerations & Best Practices

| Concern                       | Guideline                                                                                                                          |
| ----------------------------- | ---------------------------------------------------------------------------------------------------------------------------------- |
| **Idempotency**               | Callbacks may be retried by the sender; ensure your handler can safely process the same callback multiple times.                   |
| **Security / Authentication** | Use HMAC signatures or mutual TLS so you can verify that callbacks truly originate from the trusted sender.                        |
| **Timeouts & Retries**        | Decide how many times you’ll retry failed callbacks, with exponential back-off. Log and alert after exceeding thresholds.          |
| **Correlations**              | Always include a correlation ID in both request and callback for traceability and to match responses to requests.                  |
| **Ordering**                  | If events must be processed in order, consider sequence numbers or a FIFO queue rather than fire‐and‐forget webhooks.              |
| **Monitoring & Alerting**     | Track callback success/failure rates, latencies, and queue depths. Build dashboards and alerts for abnormal behaviors.             |
| **Circuit Breakers**          | If your callback target is down, avoid overwhelming it—implement circuit breakers or bulkheads to fail fast after repeated errors. |
### Common Pitfalls

1. **Tight Coupling**  
    Excessive reliance on callbacks can couple services too tightly—changes to one callback contract ripple through many services.
2. **Hidden Complexity**  
    Debugging asynchronous callbacks can be tricky; distributed tracing (e.g. OpenTelemetry) is essential.
3. **Callback Storms**  
    If a large batch of work suddenly completes, many callbacks may overwhelm the target—rate-limit or queue them.
4. **Error Handling Gaps**  
    Unhandled HTTP errors or serialization failures can silently drop callbacks—ensure robust logging and retry logic.

### Alternatives & Complementary Patterns

- **Polling**  
    Simple but less efficient; caller repeatedly queries status.
- **Reactive Streams**  
    Back-pressure–aware flows (e.g. Kafka, ReactiveX) for high-throughput event streams.
- **Pub/Sub / Event Bus**  
    Rather than point-to-point callbacks, publish events to a topic and let interested subscribers consume them.

## Load Balancer vs Gateway (AWS)

### **1. AWS Load Balancers**

AWS provides three types of load balancers:

1. **Application Load Balancer (ALB)**:
    - Operates at the **application layer (Layer 7)** of the OSI model.
    - Routes traffic based on content (e.g., URL path, hostname, HTTP headers).
    - Supports **HTTP/HTTPS** protocols.
2. **Network Load Balancer (NLB)**:
    - Operates at the **transport layer (Layer 4)** of the OSI model.
    - Routes traffic based on IP protocol data (e.g., TCP, UDP).
    - Designed for **high performance** and **low latency**.
3. **Classic Load Balancer (CLB)**:
    - Operates at both **Layer 4** and **Layer 7**.
    - Legacy load balancer, less feature-rich compared to ALB and NLB.

#### **Key Features of Load Balancers**

- **Traffic Distribution**: Distributes incoming traffic across multiple targets (e.g., EC2 instances, containers).
- **Health Checks**: Monitors the health of targets and routes traffic only to healthy targets.
- **Auto Scaling Integration**: Works seamlessly with Auto Scaling to handle traffic spikes.
- **SSL/TLS Termination**: Offloads SSL/TLS decryption to the load balancer.
- **Sticky Sessions**: Routes requests from the same client to the same target.

#### **Use Cases for Load Balancers**

- **Web Applications**: Use ALB to route HTTP/HTTPS traffic to EC2 instances, containers, or Lambda functions.
- **High-Performance Applications**: Use NLB for TCP/UDP traffic requiring low latency and high throughput.
- **Legacy Applications**: Use CLB for applications that require both Layer 4 and Layer 7 routing.

### **2. AWS API Gateway**

API Gateway is a fully managed service for creating, publishing, maintaining, monitoring, and securing **RESTful and WebSocket APIs**.

#### **Key Features of API Gateway**

- **API Management**: Acts as a front door for APIs, handling requests and routing them to backend services.
- **Traffic Management**: Supports throttling, caching, and request/response transformations.
- **Security**: Provides authentication, authorization, and protection against DDoS attacks.
- **Integration**: Integrates with AWS services like Lambda, EC2, and DynamoDB, as well as external HTTP endpoints.
- **Monitoring**: Offers detailed metrics and logging through CloudWatch.
- **Versioning**: Supports multiple versions of APIs for staged rollouts.

#### **Use Cases for API Gateway**

- **Microservices Architecture**: Use API Gateway to expose microservices as APIs.
- **Serverless Applications**: Use API Gateway to trigger Lambda functions for serverless applications.
- **Mobile and Web Backends**: Use API Gateway to build scalable backends for mobile and web applications.
- **Third-Party Integrations**: Use API Gateway to expose APIs to external partners or customers.

### **Comparison: Load Balancers vs. API Gateway**

|Feature|Load Balancer (ALB/NLB)|API Gateway|
|---|---|---|
|**Layer**|Layer 4 (NLB), Layer 7 (ALB)|Layer 7|
|**Protocols**|HTTP/HTTPS (ALB), TCP/UDP (NLB)|HTTP/HTTPS, WebSocket|
|**Traffic Routing**|Routes traffic to backend targets|Routes traffic to backend services|
|**API Management**|No|Yes|
|**Security**|SSL/TLS termination, basic auth|Authentication, authorization, DDoS protection|
|**Throttling**|No|Yes|
|**Caching**|No|Yes|
|**Integration**|EC2, containers, Lambda|Lambda, HTTP, AWS services|
|**Use Case**|Load balancing for applications|API management and exposure|

### **When to Use Which?**

#### **Use AWS Load Balancers When:**

1. You need to distribute traffic across multiple backend targets (e.g., EC2 instances, containers).
2. Your application requires high availability and fault tolerance.
3. You need SSL/TLS termination or health checks for backend targets.
4. You are building a traditional web application or a high-performance service.

#### **Use AWS API Gateway When:**

1. You need to expose RESTful or WebSocket APIs to external clients.
2. You are building a serverless application and want to trigger Lambda functions.
3. You need advanced API management features like throttling, caching, or request/response transformations.
4. You are building a microservices architecture and need a centralized API management layer.

### **Example Scenarios**

1. **Web Application with EC2 Backend**:
    - Use **ALB** to distribute HTTP/HTTPS traffic across EC2 instances.
2. **Serverless Application**:
    - Use **API Gateway** to trigger Lambda functions for serverless APIs.
3. **High-Performance TCP Service**:
    - Use **NLB** to distribute TCP traffic across backend instances.
4. **Microservices Architecture**:
    - Use **API Gateway** to expose microservices as RESTful APIs.

### **Conclusion**

- **AWS Load Balancers** are ideal for distributing traffic across backend targets and ensuring high availability.
- **AWS API Gateway** is designed for managing, securing, and exposing APIs to external clients.
- Choose **Load Balancers** for traditional web applications or high-performance services.
- Choose **API Gateway** for API management, serverless applications, or microservices architectures.