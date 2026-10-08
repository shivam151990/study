package com.shivam151990.lld.coupon;

import com.shivam151990.lld.coupon.model.Coupon;
import com.shivam151990.lld.coupon.model.Product;
import com.shivam151990.lld.coupon.repo.CouponRepository;
import com.shivam151990.lld.coupon.repo.InMemoryCouponRepository;
import com.shivam151990.lld.coupon.service.BuyXGetYCouponDiscount;
import com.shivam151990.lld.coupon.service.CouponApplicationResult;
import com.shivam151990.lld.coupon.service.CouponService;
import com.shivam151990.lld.coupon.service.FlatAmountCouponDiscount;
import com.shivam151990.lld.coupon.service.PercentageCouponDiscount;

import java.time.LocalDate;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        List<Product> basket = List.of(
                new Product("p1", "Milk", 2.0),
                new Product("p2", "Milk", 2.0),
                new Product("p3", "Bread", 1.0),
                new Product("p4", "Chocolate", 3.0)
        );

        LocalDate today = LocalDate.now();
        Coupon save10 = new Coupon("SAVE10", new PercentageCouponDiscount(10),
                today.minusDays(1), today.plusDays(10), 0, 3);
        Coupon flat5 = new Coupon("FLAT5", new FlatAmountCouponDiscount(5),
                today.minusDays(1), today.plusDays(10), 20, 3);
        Coupon milk2 = new Coupon("MILK2", new BuyXGetYCouponDiscount("Milk", 2, 1),
                today.minusDays(1), today.plusDays(10), 0, 3);

        CouponRepository repo = new InMemoryCouponRepository();
        repo.addCoupon(save10);
        repo.addCoupon(flat5);
        repo.addCoupon(milk2);

        CouponService couponService = new CouponService(repo);
        CouponApplicationResult result = couponService.computeFinalPriceAfterCoupons("customer-1", basket);

        System.out.println("Applied coupon: " + result.appliedCouponCode().orElse("none"));
        System.out.println("Final price: " + result.finalPrice());
    }
}
