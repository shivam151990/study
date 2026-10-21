package com.shivam151990.lld.coupon_v1.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class Coupon {

    protected final String code;
    protected final int value;
    protected final int minBasketValue;
    protected final LocalDateTime start;
    protected final LocalDateTime end;
    protected final int totalUsageLimit;
    protected int usageCount;

    public Coupon(String code, int value, int minBasketValue, LocalDateTime start, LocalDateTime end, int totalUsageLimit) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Coupon code is required");
        }
        if (value <= 0 || minBasketValue < 0 || totalUsageLimit <= 0) {
            throw new IllegalArgumentException("Invalid coupon values");
        }
        if (start == null || end == null || end.isBefore(start)) {
            throw new IllegalArgumentException("Invalid validity window");
        }
        this.code = code;
        this.value = value;
        this.minBasketValue = minBasketValue;
        this.start = start;
        this.end = end;
        this.totalUsageLimit = totalUsageLimit;
    }

    public abstract CouponType getType();

    /** Discount amount this coupon gives on the basket; never more than the basket total. */
    public abstract long getDiscount(long basketTotal);

    public long apply(long basketTotal) {
        return basketTotal - getDiscount(basketTotal);
    }

    public void incrementUsage() {
        usageCount++;
    }
}
