package com.shivam151990.lld.coupon_v1.model;

import java.time.LocalDateTime;

public class FlatCoupon extends Coupon {

    public FlatCoupon(String code, int value, int minBasketValue, LocalDateTime start, LocalDateTime end, int totalUsageLimit) {
        super(code, value, minBasketValue, start, end, totalUsageLimit);
    }

    @Override
    public CouponType getType() {
        return CouponType.FLAT;
    }

    @Override
    public long getDiscount(long basketTotal) {
        return Math.min(value, basketTotal);
    }
}
