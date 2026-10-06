As part of the Data minimisation efforts and GDPR, Tesco wants to implement a system that flags user
accounts which are ready to delete based on the below rules. More rules will be introduced later.
· If a user has not performed any valid activity within a specified inactivity period (e.g., 90 days),
their account is ready for deletion. Certain activities (e.g., market email interactions) 
are ignored when deciding the last valid interaction.
· User raised a request to delete the account. This could be managed by a flag set at the account level by an external process,
which is out of the scope of this exercise.
Input
1. A list of users, where each user is represented by a unique identifier
2. A list of activities, where each activity corresponds to an interaction performed by a user (e.g., website login, mobile app login, store purchase, market email interaction, etc.).
   Output
   A list of users whose accounts are ready for deletion.
----Constraints
1. The input lists can contain up to 10^5 elements.
2. Timestamps are in ISO 8601 format.
   Example
   Input
   Users
   · User 1
   · User 2
   · User 3

Time now
2024-05-01 11:00:00.000

Output
User 3