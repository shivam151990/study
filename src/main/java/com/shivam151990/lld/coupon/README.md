Problem

A supermarket wants to give customers discounts using coupons. Design the classes for a system that does two things:

Stores coupons.
Takes a customer's shopping basket and says which coupons apply and what the final price is.
An example

A basket holds: Milk £2 ×2, Bread £1 ×1, Chocolate £3 ×1.

Coupon	Rule
SAVE10	10% off the whole basket
FLAT5	£5 off if the basket is over £20
MILK2	Buy 2 milk, get £1 off

The system checks each coupon and picks the best valid one, or applies them if they are allowed to combine. It returns the final price.

Keep it to four rules
1. Coupon types: percentage off, flat amount off, and buy-X-get-Y. Adding a new type later should not mean editing existing code.
2. Validity: a coupon has a start and end date, and it can have a minimum basket value.
3. Usage limit: each customer can use a coupon at most N times.
4. One coupon per basket to begin with.