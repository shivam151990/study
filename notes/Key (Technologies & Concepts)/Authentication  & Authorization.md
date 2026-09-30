## Authentication

### Overview

**Authentication** is the process of verifying the identity of a user, device, or service before granting access to resources in a web application. It answers the question “Who are you?” (whereas **authorization** answers “What are you allowed to do?”). Robust authentication is foundational for security, ensuring that only legitimate actors can interact with protected parts of an application.

#### Authentication Factors
Authentication methods can be categorized by the “factor” they rely on:

1. **Something you know**
    - Passwords, PINs, security questions

2. **Something you have**
    - One-time tokens (hardware or software), smart cards, mobile devices
        
3. **Something you are**
    - Biometrics (fingerprint, facial recognition, voice)
        
4. **Something you do** (behavioral)
    - Typing rhythm, gait
        
5. **Somewhere you are**
    - Geolocation, IP allow‐lists

Multi‑factor authentication (MFA) combines two or more factors for stronger assurance.

#### Common Web Authentication Methods

##### 1. Password-Based Authentication
- **Overview**: User submits username/email and password via a login form.
- **Session Management**: On success, server creates a session and issues a session cookie.
- **Pros & Cons**: Easy to implement but vulnerable to phishing, brute‑force, and credential stuffing. Requires strong password policies (complexity, rotation).

##### 2. Token-Based Authentication
- **JSON Web Tokens (JWT)**:
    - Server issues a signed JWT after login.
    - Client stores the token (e.g., in local storage) and sends it in an `Authorization: Bearer <token>` header.
    - Stateless: server verifies signature on each request.

- **Pros & Cons**: Scalability (no server‑side session store), but tokens can’t easily be revoked and risk XSS if stored insecurely.

##### 3. OAuth 2.0 (Authorization Framework)
**OAuth 2.0** is an **authorization framework** that allows third-party applications to access user resources (e.g., data, services) hosted on a server without exposing the user's credentials. It is widely used to enable secure and controlled access to APIs, web services, and other resources. Here’s a detailed explanation of OAuth 2.0, its components, and how it works:

###### **What is OAuth 2.0?**
- **Purpose**:
    - OAuth 2.0 enables **authorization** (granting access) rather than **authentication** (verifying identity).
    - It allows users to grant limited access to their resources (e.g., files, photos, or APIs) to third-party applications without sharing their passwords.
- **Use Cases**:
    - Logging in to an app using a social media account (e.g., "Log in with Google").
    - Allowing a third-party app to access your data (e.g., a calendar app accessing your Google Calendar).
    - Granting access to APIs (e.g., a mobile app accessing a backend API).

##### 4. OpenID Connect (OIDC)

**OpenID Connect (OIDC)** is an authentication protocol built on top of **OAuth 2.0**. It adds an identity layer to OAuth 2.0, enabling applications to verify the identity of users and obtain basic profile information about them. Here’s a detailed explanation of what OIDC is, how it works, and its key components:

###### **What is OpenID Connect (OIDC)?**
- **Purpose**:
    - OIDC is designed for **authentication** (verifying user identity) and provides a standardized way to authenticate users across different applications and services.
- **Built on OAuth 2.0**:
    - OIDC extends OAuth 2.0, which is primarily used for **authorization** (granting access to resources), by adding mechanisms for authentication.
- **Key Output**:
    - OIDC issues an **ID token** (a JSON Web Token or JWT) that contains information about the authenticated user.

##### 5. SAML (Security Assertion Markup Language)

SAML (Security Assertion Markup Language)** is an **XML-based standard** for exchanging authentication and authorization data between parties, particularly between an **Identity Provider (IdP)** and a **Service Provider (SP)**. It is widely used for **Single Sign-On (SSO)**, enabling users to log in once and access multiple applications without re-entering credentials. Here’s a detailed explanation of SAML, its components, and how it works:

###### **What is SAML?**
- **Purpose**:
    - SAML is designed for **authentication** (verifying user identity) and **authorization** (granting access to resources).
    - It enables secure communication between an **Identity Provider (IdP)** and a **Service Provider (SP)** to authenticate users and grant access to applications.
- **Use Cases**:
    - Single Sign-On (SSO) for enterprise applications.
    - Federated identity systems (e.g., allowing users to log in to multiple systems using a single identity).

##### 6. API Key Authentication
An **API key** is a unique string of characters (a kind of secret token) that identifies and authenticates whoever is making a request to an API. It's essentially a password, but designed for programs/services to use rather than humans typing credentials into a login form.

**What it does**
When you call an API (say, a weather service, a payment processor, or an AI model provider), the service needs to know:

1. **Who is making this request** (identification)
2. **Are they allowed to make it** (authorization)
3. **How much have they used, and should they be rate-limited or billed** (tracking/quota)

An API key handles all of this. You typically include it in your request — often as a header (`Authorization: Bearer sk-abc123...` or `X-API-Key: abc123...`) or sometimes as a query parameter — and the server checks it against its records before processing the request.

##### 7. Client Certificate Authentication (mTLS)
**Client Certificate Authentication**, also known as **Mutual TLS (mTLS)**, is a secure method of authenticating clients (e.g., users, devices, or applications) in a client-server communication. It is an extension of the **Transport Layer Security (TLS)** protocol, which is commonly used to encrypt HTTPS traffic. In mTLS, **both the client and the server present digital certificates** to verify their identities, ensuring a highly secure and trusted connection. Here’s a detailed explanation of Client Certificate Authentication (mTLS), how it works, and its pros and cons:

###### **What is Client Certificate Authentication (mTLS)?**
- **Purpose**:
    - To authenticate both the client and the server using digital certificates, ensuring that only trusted parties can communicate.
- **How It Works**:
    - The client and server exchange and validate each other’s digital certificates during the TLS handshake.
    - The server verifies the client’s certificate, and the client verifies the server’s certificate.
    - If both certificates are valid, the connection is established.

##### 8. Passwordless Authentication
**Passwordless Authentication** is a modern authentication method that eliminates the need for traditional passwords. Instead, it relies on alternative factors to verify a user’s identity, such as biometrics (e.g., fingerprint, facial recognition), possession of a device (e.g., smartphone, hardware token), or cryptographic keys. This approach enhances security and improves user experience by removing the vulnerabilities and frustrations associated with passwords. Here’s a detailed explanation of Passwordless Authentication, how it works, and its pros and cons:

###### **What is Passwordless Authentication?**
- **Purpose**:
    - To authenticate users without requiring them to enter a password, reducing the risks of password-related attacks (e.g., phishing, brute force, credential stuffing).
- **Key Principle**:
    - Replace passwords with more secure and user-friendly authentication factors.
- **Common Methods**:
    - **Biometrics**: Fingerprint, facial recognition, or iris scan.
    - **Possession Factors**: One-time passwords (OTPs), push notifications, or hardware tokens.
    - **Cryptographic Keys**: Public/private key pairs (e.g., WebAuthn, FIDO2).


### OAuth 2.0
OAuth 2.0 isn’t an authentication protocol but rather an authorization protocol. Its main purpose is to give access to resources like user data, remote APIs, and so on. The difference between authorization and authentication can often be tricky to understand. The **authentication process** involves verifying who the user is. Once a user has been authenticated, the **authorization process** involves deciding which resources a user can access and modify.
OAuth 2.0 does its main job with the help of access tokens. An **access token**, which is often a JSON Web Token (JWT) formatted token, is used to access resources in place of the user. The token issuers can add the data of their choice to these tokens. Moreover, the access tokens may have an expiration date for security purposes.

#### OAuth 2.0 Components:

1. **Roles**:
    - **Resource Owner**: The entity that owns the secured resources and can grant access to them.
    - **Client**: The application or entity requesting access to the secured resources. It requires an access token to gain access.
    - **Authorization Server**: Authenticates the client, obtains consent from the resource owner, and issues access tokens. It includes:
        - **Authorization Endpoint**: Handles user authentication and consent.
        - **Token Endpoint**: Used for machine-to-machine communication to issue tokens.
    - **Resource Server**: Hosts the secured resources. It verifies the access token provided by the client and returns the requested resources.
2. **Scopes**:
    - Scopes define the specific permissions an application requests to access a user’s resources.
    - They are displayed to the user on the consent screen, and the access token issued is limited to the granted scopes.
    - OAuth 2.0 does not define specific scope values; they are determined by the resource server.

#### Workflow
https://www.youtube.com/watch?v=VZH_lGxqFYU

https://www.youtube.com/watch?v=ZDuRmhLSLOY



## Extras
### Digital Certificates

#### Links

Digital Signature - https://www.youtube.com/watch?v=TmA2QWSLSPg&t=2s

Digital Certificate - https://www.youtube.com/watch?v=UbMlPIgzTxc

PKI (Public Key Infrastructure)- https://www.youtube.com/watch?v=0ctat6RBrFo

**Digital Certificates** are electronic documents used to verify the identity of individuals, devices, or organizations in digital communications. They play a critical role in ensuring secure communication, authentication, and data integrity in systems like **HTTPS**, **SSL/TLS**, **VPNs**, and **email encryption**. Here’s a detailed explanation of digital certificates, their components, how they work, and their use cases:

#### **What is a Digital Certificate?**

- **Definition**:
    - A digital certificate is a file that binds a public key to an entity (e.g., a person, device, or organization) and verifies its identity.
- **Purpose**:
    - To establish trust in digital communications by confirming the identity of the certificate holder and enabling secure encryption.
- **Issued By**:
    - A **Certificate Authority (CA)**, a trusted third party that validates the certificate holder’s identity and signs the certificate.

#### **Key Components of a Digital Certificate**:

1. **Public Key**:
    - The public key of the certificate holder, used for encryption and verification.
2. **Subject**:
    - The entity the certificate is issued to (e.g., domain name, organization, or individual).
3. **Issuer**:
    - The Certificate Authority (CA) that issued the certificate.
4. **Validity Period**:
    - The start and end dates during which the certificate is valid.
5. **Digital Signature**:
    - A cryptographic signature created by the CA to ensure the certificate’s authenticity and integrity.
6. **Serial Number**:
    - A unique identifier for the certificate.
7. **Extensions**:
    - Additional information, such as key usage (e.g., encryption, signing) or subject alternative names (e.g., multiple domain names).

#### **How Digital Certificates Work**:
1. **Certificate Issuance**:
    - An entity generates a public/private key pair.
    - The entity submits a **Certificate Signing Request (CSR)** to a CA, which includes the public key and identity information.
    - The CA verifies the entity’s identity and issues a digital certificate signed with its private key.
2. **Certificate Verification**:
    - When a client (e.g., a browser or application) receives a digital certificate, it verifies:
        - The CA’s signature using the CA’s public key.
        - The certificate’s validity period.
        - The certificate’s revocation status (e.g., using a **Certificate Revocation List (CRL)** or **Online Certificate Status Protocol (OCSP)**).
3. **Secure Communication**:
    - The client uses the certificate’s public key to encrypt data or verify digital signatures.
    - The server uses its private key to decrypt data or sign messages.

#### **Types of Digital Certificates**:

1. **SSL/TLS Certificates**:
    - Used to secure websites (HTTPS) and encrypt communication between a browser and a server.
    - Types:
        - **Domain Validated (DV)**: Basic validation of domain ownership.
        - **Organization Validated (OV)**: Validation of the organization’s identity.
        - **Extended Validation (EV)**: Rigorous validation, often displaying the organization’s name in the browser’s address bar.
2. **Code Signing Certificates**:
    - Used to sign software or code to verify its authenticity and integrity.
3. **Email Certificates**:
    - Used for encrypting and signing emails (e.g., S/MIME certificates).
4. **Client Certificates**:
    - Used to authenticate clients in systems like **Mutual TLS (mTLS)**.
5. **Root Certificates**:
    - Self-signed certificates issued by CAs, used to sign other certificates.

#### **How Digital Certificates Are Used**:
1. **HTTPS (SSL/TLS)**:
    - Websites use SSL/TLS certificates to encrypt communication between the browser and server.
    - Example: When you visit `https://example.com`, the browser verifies the site’s certificate and establishes a secure connection.
2. **Mutual TLS (mTLS)**:
    - Both the client and server present digital certificates to authenticate each other.
    - Example: Used in enterprise systems, APIs, and IoT devices.
3. **Email Encryption**:
    - Email certificates (e.g., S/MIME) are used to encrypt and sign emails, ensuring confidentiality and authenticity.
4. **Code Signing**:
    - Developers use code signing certificates to sign software, ensuring it hasn’t been tampered with.
5. **VPNs**:
    - Digital certificates are used to authenticate devices and users in VPNs.

#### **Advantages of Digital Certificates**:
1. **Strong Authentication**:
    - Verifies the identity of individuals, devices, or organizations.
2. **Data Encryption**:
    - Enables secure communication by encrypting data.
3. **Data Integrity**:
    - Ensures that data has not been tampered with during transmission.
4. **Trust**:
    - Establishes trust in digital communications through CA validation.
5. **Compliance**:
    - Helps meet regulatory requirements for security and privacy (e.g., GDPR, HIPAA).

#### **Disadvantages of Digital Certificates**:
1. **Complexity**:
    - Requires setting up and managing a Public Key Infrastructure (PKI).
2. **Cost**:
    - Certificates issued by trusted CAs can be expensive.
3. **Certificate Management**:
    - Certificates must be renewed, revoked, and replaced periodically.
4. **Dependency on CAs**:
    - If a CA is compromised, the trust in its certificates is lost.

#### **Best Practices for Digital Certificates**:
1. **Use Trusted CAs**:
    - Obtain certificates from reputable Certificate Authorities.
2. **Regular Renewal**:
    - Renew certificates before they expire to avoid service disruptions.
3. **Revocation Checks**:
    - Use CRLs or OCSP to check for revoked certificates.
4. **Secure Storage**:
    - Store private keys securely (e.g., using hardware security modules or HSMs).
5. **Monitor and Audit**:
    - Monitor certificate usage and audit for compliance.

#### **Example Workflow (HTTPS)**:

1. A user visits `https://example.com`.
2. The server sends its SSL/TLS certificate to the browser.
3. The browser verifies the certificate’s validity and checks the CA’s signature.
4. If the certificate is valid, the browser establishes an encrypted connection with the server.
5. The user and server can now communicate securely.

#### **Summary**:
Digital Certificates are essential for secure digital communication, providing authentication, encryption, and data integrity. They are widely used in HTTPS, mTLS, email encryption, and code signing. While they require careful management and infrastructure, they are a cornerstone of modern cybersecurity.

### Authorization Server vs Identity Provider

he **Authorization Server** and **Identity Provider (IdP)** are both critical components in authentication and authorization systems, but they serve distinct purposes. Here's a detailed comparison:

---

#### **1. Authorization Server**:

- **Purpose**:
    - Handles **authorization** by issuing access tokens to clients (applications) after verifying their permissions.
    - Ensures that clients can access specific resources on behalf of a user.
- **Key Functions**:
    - Authenticates the client (application).
    - Obtains **consent** from the resource owner (user).
    - Issues **access tokens** and optionally **refresh tokens**.
    - Supports endpoints like the **Authorization Endpoint** (for user consent) and the **Token Endpoint** (for token issuance).
- **Use Case**:
    - Used in **OAuth 2.0** to manage access to resources (e.g., APIs, data) by validating and issuing tokens.
- **Example**:
    - A service like **Auth0** or **AWS Cognito** can act as an authorization server.

---

#### **2. Identity Provider (IdP)**:

- **Purpose**:
    - Handles **authentication** by verifying the identity of users.
    - Provides proof of a user’s identity to other systems or applications.
- **Key Functions**:
    - Authenticates users (e.g., via username/password, social logins, or multi-factor authentication).
    - Issues **identity tokens** (e.g., **ID tokens** in OpenID Connect) that contain user identity information.
    - Manages user identities, credentials, and attributes (e.g., name, email, roles).
- **Use Case**:
    - Used in **Single Sign-On (SSO)** and **federated identity** systems to allow users to log in once and access multiple applications.
- **Example**:
    - Services like **Google**, **Microsoft Azure AD**, or **Okta** can act as identity providers.

---

#### **Key Differences**:

|**Aspect**|**Authorization Server**|**Identity Provider (IdP)**|
|---|---|---|
|**Primary Role**|Manages **authorization** (access to resources).|Manages **authentication** (user identity).|
|**Tokens Issued**|Issues **access tokens** for resource access.|Issues **identity tokens** (e.g., ID tokens).|
|**Focus**|Ensures clients have the right permissions.|Verifies and manages user identities.|
|**Protocols**|Primarily used in **OAuth 2.0**.|Used in **OpenID Connect (OIDC)** and SSO.|
|**Endpoints**|Authorization Endpoint, Token Endpoint.|Authentication Endpoint, UserInfo Endpoint.|
|**Example Use Case**|Grants access to an API or data.|Verifies a user’s identity for login.|

---

#### **How They Work Together**:

- In systems like **OpenID Connect (OIDC)**, the **Identity Provider** and **Authorization Server** often work together:
    1. The **IdP** authenticates the user and issues an **ID token**.
    2. The **Authorization Server** issues an **access token** to the client, allowing it to access resources on behalf of the user.
- Example: When you log in to an app using Google:
    - Google (IdP) verifies your identity and issues an ID token.
    - Google (Authorization Server) issues an access token for the app to access your Google resources (e.g., Gmail, Drive).

---

#### **Summary**:

- **Authorization Server**: Focuses on granting access to resources by issuing access tokens.
- **Identity Provider**: Focuses on verifying user identity and issuing identity tokens.
- Together, they enable secure authentication and authorization in modern systems like OAuth 2.0 and OpenID Connect.

### JWT in Authentication vs Authorization

**JWT (JSON Web Token)** plays a crucial role in both **authentication** and **authorization** processes in modern systems. Here's how it fits into each:

#### **1. JWT in Authentication**:

- **Purpose**:
    - Used to prove the identity of a user after they have been authenticated.
- **How It Works**:
    - After a user logs in (e.g., via username/password or social login), the **Identity Provider (IdP)** generates a **JWT** (often called an **ID token**) that contains information about the user (e.g., user ID, email, roles).
    - This JWT is signed by the IdP to ensure its authenticity and integrity.
    - The JWT is sent to the client (e.g., a web or mobile app) and can be used to prove the user's identity in subsequent requests.
- **Use Case**:
    - In **OpenID Connect (OIDC)**, JWTs are used as **ID tokens** to represent the authenticated user.
    - Example: When you log in to an app using Google, Google issues a JWT (ID token) that contains your user information.

---

#### **2. JWT in Authorization**:

- **Purpose**:
    - Used to grant access to specific resources or actions.
- **How It Works**:
    - After authentication, the **Authorization Server** issues a **JWT** (often called an **access token**) that contains information about the permissions granted to the client (e.g., scopes, roles).
    - This JWT is signed by the Authorization Server to ensure its authenticity and integrity.
    - The client includes this JWT in requests to the **Resource Server** (e.g., an API) to access protected resources.
    - The Resource Server verifies the JWT and checks the permissions (e.g., scopes) before allowing access.
- **Use Case**:
    - In **OAuth 2.0**, JWTs are often used as **access tokens** to authorize access to APIs or other resources.
    - Example: When an app accesses your Google Drive, it includes a JWT (access token) in the request to prove it has permission to access your files.

---

#### **Key Features of JWTs**:

1. **Self-Contained**:
    - JWTs contain all the necessary information (e.g., user identity, permissions) in a compact, JSON-based format.
2. **Signed**:
    - JWTs are signed by the issuer (e.g., IdP or Authorization Server) to ensure they cannot be tampered with.
3. **Stateless**:
    - JWTs do not require server-side storage, making them scalable for distributed systems.
4. **Expiration**:
    - JWTs have an expiration time (`exp` claim) to limit their validity and improve security.

---

#### **How JWTs Fit into Authentication and Authorization**:

|**Aspect**|**Authentication**|**Authorization**|
|---|---|---|
|**Token Type**|**ID Token** (JWT issued by IdP).|**Access Token** (JWT issued by Authorization Server).|
|**Purpose**|Proves the user’s identity.|Grants access to specific resources.|
|**Contents**|User information (e.g., `sub`, `email`, `roles`).|Permissions (e.g., `scopes`, `roles`).|
|**Issuer**|Identity Provider (IdP).|Authorization Server.|
|**Verifier**|Client or Resource Server.|Resource Server.|
|**Example Use Case**|User logs in and receives an ID token.|App accesses an API using an access token.|

---

#### **Example Flow**:

1. **Authentication**:
    - User logs in using their credentials.
    - IdP issues a JWT (ID token) containing user information.
2. **Authorization**:
    - Client requests an access token from the Authorization Server.
    - Authorization Server issues a JWT (access token) containing permissions.
3. **Resource Access**:
    - Client includes the access token in API requests.
    - Resource Server verifies the token and grants access based on its contents.

---

#### **Summary**:

- **Authentication**: JWTs (ID tokens) are used to prove the user’s identity.
- **Authorization**: JWTs (access tokens) are used to grant access to resources.
- JWTs are self-contained, signed, and stateless, making them ideal for secure and scalable authentication and authorization in modern systems like OAuth 2.0 and OpenID Connect.