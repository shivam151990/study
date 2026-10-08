package com.shivam151990.lld.coupon.repo;

import com.shivam151990.lld.coupon.model.Coupon;

import java.util.List;

public interface CouponRepository {

    void addCoupon(Coupon coupon);

    List<Coupon> getAllCoupons();

    int getUsageCount(String customerId, String couponCode);

    void recordUsage(String customerId, String couponCode);
}
