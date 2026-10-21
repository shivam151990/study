package com.shivam151990.lld.coupon_v1.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PercentCoupon extends Coupon {

    private static final long NO_CAP = Long.MAX_VALUE;

    // Max discount amount (not percent) this coupon can give
    private final long maxDiscountCap;

    public PercentCoupon(String code, int value, int minBasketValue, LocalDateTime start, LocalDateTime end, int totalUsageLimit) {
        this(code, value, minBasketValue, start, end, totalUsageLimit, NO_CAP);
    }

    public PercentCoupon(String code, int value, int minBasketValue, LocalDateTime start, LocalDateTime end, int totalUsageLimit, long maxDiscountCap) {
        super(code, value, minBasketValue, start, end, totalUsageLimit);
        if (value > 100) {
            throw new IllegalArgumentException("Percent cannot exceed 100");
        }
        if (maxDiscountCap <= 0) {
            throw new IllegalArgumentException("Max discount cap must be positive");
        }
        this.maxDiscountCap = maxDiscountCap;
    }

    @Override
    public CouponType getType() {
        return CouponType.PERCENT;
    }

    @Override
    public long getDiscount(long basketTotal) {
        long discount = (basketTotal * value) / 100;
        return Math.min(discount, maxDiscountCap);
    }
}
