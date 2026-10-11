"A food delivery app receives orders continuously. 
For every new order, pick a delivery partner and assign them. 
The selection rule may change over time, for example nearest partner today and highest rating tomorrow."

Example:
Partners P1 (1 km away, available), P2 (3 km away, available), P3 (0.5 km away, busy). 
A new order is assigned to P1, the nearest available partner. P1 is now marked busy. 
After complete(order), P1 is available again.

Requirements:
1. Partners have an id, a location and an available flag.
2. Assign(order) picks a partner using a selection rule and marks them busy. If none is available, throw an error.
3. Complete(order) frees the partner.
4. The selection rule sits behind a Strategy interface, with one implementation: nearest partner.

Mention only: rejection and reassignment, load-based rules, offering an order to several partners.