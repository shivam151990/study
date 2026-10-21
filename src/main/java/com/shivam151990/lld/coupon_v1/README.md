Coupon Management

Requirements

1. Create a coupon with: code, type (PERCENT or FLAT), value, minimum basket value, validity (start, end), and total usage limit.
2. apply(code, basketTotal, now) returns the discounted total, or throws an error with a reason: unknown code, not started or expired, below minimum, or usage limit reached.
3. A percent coupon may have a max discount cap.
4. A successful apply increases the coupon’s usage count by one.
5. getBestCoupon(basketTotal, now) returns the code giving the highest discount among valid coupons.