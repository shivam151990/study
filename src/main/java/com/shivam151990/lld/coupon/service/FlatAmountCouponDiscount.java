package com.shivam151990.lld.coupon.service;

import com.shivam151990.lld.coupon.model.Product;

import java.util.List;

public class FlatAmountCouponDiscount implements CouponDiscount {

    private final double amountOff;

    public FlatAmountCouponDiscount(double amountOff) {
        this.amountOff = amountOff;
    }

    @Override
    public double discount(List<Product> basket) {
        double total = basket.stream().mapToDouble(Product::getPrice).sum();
        return Math.min(amountOff, total);
    }
}
