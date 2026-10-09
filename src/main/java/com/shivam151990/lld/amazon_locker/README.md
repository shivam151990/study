"Amazon places parcel lockers in stores and apartment complexes. 
A delivery agent drops a package into a free compartment, and the customer later collects it using a code. 
Design the classes for the locker system and implement the deposit and pickup flow."

Example
A locker has 2 Small, 2 Medium and 1 Large compartment. An agent deposits a Medium package and gets code 482913. Later the customer enters 482913 and compartment M1 opens and becomes free. Entering 482913 again fails.

Requirements:
1. 3 compartment sizes: Small, Medium, Large. One locker location with a fixed set of compartments.
2. deposit(packageSize) assigns the smallest free compartment that fits and returns a pickup code. 
   If none fits, it throws an error.
3. pickup(code) opens the compartment, frees it and invalidates the code. A wrong or already-used code throws an error.

Mention only: 3-day expiry, multiple locations, lockout after wrong codes.