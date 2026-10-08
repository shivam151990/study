package com.shivam151990.lld.coupon.model;

import com.shivam151990.lld.coupon.service.CouponDiscount;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Coupon {

    private final String code;
    private final CouponDiscount couponDiscount;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final double minBasketValue;
    private final int maxUsagePerCustomer;

    public Coupon(String code, CouponDiscount couponDiscount, LocalDate startDate, LocalDate endDate,
                  double minBasketValue, int maxUsagePerCustomer) {
        this.code = code;
        this.couponDiscount = couponDiscount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.minBasketValue = minBasketValue;
        this.maxUsagePerCustomer = maxUsagePerCustomer;
    }

    public boolean isValid(LocalDate today, double basketTotal) {
        return !today.isBefore(startDate) && !today.isAfter(endDate) && basketTotal >= minBasketValue;
    }
}
