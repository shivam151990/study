Loyalty Points (Clubcard-style)


Requirements:

1. earn(userId, purchaseAmount, category) adds points. Rate is 1 point per ₹100, and a category can override its rate 
   for example 2 points per ₹100 for fresh produce).
2. redeem(userId, points) deducts points. It fails if the balance is too low.
3. Points expire 12 months after they were earned, and redemption uses the oldest points first.
4. getBalance(userId, now) counts only unexpired points.
5. getHistory(userId) lists earn and redeem events in order.