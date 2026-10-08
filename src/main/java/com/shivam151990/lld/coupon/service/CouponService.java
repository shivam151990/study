package com.shivam151990.lld.coupon.service;

import com.shivam151990.lld.coupon.model.Coupon;
import com.shivam151990.lld.coupon.model.Product;
import com.shivam151990.lld.coupon.repo.CouponRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public CouponApplicationResult computeFinalPriceAfterCoupons(String customerId, List<Product> basket) {
        double basketTotal = basket.stream().mapToDouble(Product::getPrice).sum();
        LocalDate today = LocalDate.now();

        Coupon bestCoupon = null;
        double bestDiscount = 0;

        for (Coupon coupon : couponRepository.getAllCoupons()) {
            if (!coupon.isValid(today, basketTotal)) {
                continue;
            }
            if (couponRepository.getUsageCount(customerId, coupon.getCode()) >= coupon.getMaxUsagePerCustomer()) {
                continue;
            }

            double discount = coupon.getCouponDiscount().discount(basket);
            if (discount > bestDiscount) {
                bestDiscount = discount;
                bestCoupon = coupon;
            }
        }

        if (bestCoupon == null) {
            return new CouponApplicationResult(Optional.empty(), basketTotal);
        }

        couponRepository.recordUsage(customerId, bestCoupon.getCode());
        return new CouponApplicationResult(Optional.of(bestCoupon.getCode()), basketTotal - bestDiscount);
    }
}
