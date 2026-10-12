"Design a multi-floor parking lot. When a vehicle arrives it should get a spot and a ticket. 
When it leaves, the system should charge it based on how long it stayed and free the spot."

Example
A lot has 2 floors, each with 2 Motorcycle, 3 Compact and 1 Large spot. 
A car enters at 10:00 and gets Floor 1, spot C1 and ticket T1. 
It leaves at 12:30 and is charged for 3 hours (rounded up) at the Compact rate.

Requirements:
1. 3 spot types: Motorcycle, Compact, Large. A vehicle gets a spot of its own type.
2. park(vehicle) returns a ticket with the spot. exit(ticket) returns the fee and frees the spot.
3. Flat hourly rate per vehicle type, rounded up to whole hours.
4. Reject entry when no spot of that type is free.