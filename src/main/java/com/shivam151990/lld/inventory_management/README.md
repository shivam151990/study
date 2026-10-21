"An online store keeps an in-memory inventory of products. 
Customers fill a shopping cart and check out; checkout must reserve the stock and return an invoice. 
Many customers check out at the same time, so the inventory must never be oversold. 
Design the classes and implement the add-stock and checkout flow."

Example
Inventory has A x2 and B x1. Customer 1's cart is {A: 1, B: 1} and Customer 2's cart is {B: 1}, and both check out at the same time.
Exactly one of them gets an invoice; the other fails because only one B was in stock. 
Inventory ends as A x1, B x0 (if Customer 1 won) or A x2, B x0 (if Customer 2 won), never with B below zero.
A cart of {A: 5} fails and leaves the inventory unchanged.

Requirements:
1. addProduct(productId) adds one unit of stock, creating the product if it doesn't exist yet.
2. A shopping cart maps productId to quantity.
3. checkoutCart(cart) books every item in the cart and returns an invoice. 
   The booking is all-or-nothing: if any item is missing or short on stock, nothing is booked.
4. checkAvailability(cart) tells whether the cart can currently be fulfilled (a hint only, it may be stale by checkout).
5. Thread safety: concurrent checkouts and restocks must not oversell, lose updates or deadlock, 
   including when two carts contain the same products in a different order.

Mention only: payment and order lifecycle, cart expiry and releasing reservations, multiple warehouses, persistent storage.
