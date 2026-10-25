Build the backend for ordering from restaurants. Each restaurant has a menu with prices.

1. Restaurants can only handle a fixed number of active orders at once.
2. A customer orders items with quantities from one restaurant. 
   the order is rejected if the restaurant is at capacity or an item isn’t on its menu.
3. Completing an order frees up capacity. 
4. A customer can search for an item by name and see which restaurants serve it, cheapest first.

resturant -> order Count
Resturant -> Map(user, Order)

