package com.shivam151990.lld.coupon.service;

import com.shivam151990.lld.coupon.model.Product;

import java.util.List;

public class PercentageCouponDiscount implements CouponDiscount {

    private final int percentage;

    public PercentageCouponDiscount(int percentage) {
        this.percentage = percentage;
    }

    @Override
    public double discount(List<Product> basket) {
        double total = basket.stream().mapToDouble(Product::getPrice).sum();
        return total * percentage / 100;
    }
}
