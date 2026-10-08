package com.shivam151990.lld.coupon.repo;

import com.shivam151990.lld.coupon.model.Coupon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryCouponRepository implements CouponRepository {

    private final List<Coupon> coupons = new ArrayList<>();
    private final Map<String, Integer> usageCounts = new HashMap<>();

    @Override
    public void addCoupon(Coupon coupon) {
        coupons.add(coupon);
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return coupons;
    }

    @Override
    public int getUsageCount(String customerId, String couponCode) {
        return usageCounts.getOrDefault(customerId + ":" + couponCode, 0);
    }

    @Override
    public void recordUsage(String customerId, String couponCode) {
        usageCounts.merge(customerId + ":" + couponCode, 1, Integer::sum);
    }
}
