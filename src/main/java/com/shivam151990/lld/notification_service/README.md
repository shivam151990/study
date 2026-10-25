Notification Service

1. Channels: EMAIL, SMS, PUSH. Each sends a message (print is fine).
2. Each user sets preferred channels: setPreferences(userId, channels).
3. send(userId, message) delivers on every preferred channel. A user with no preferences gets EMAIL.
4. A failing channel doesn’t stop the others, and failures are recorded.
5. Adding a new channel needs no change to send.