package com.shivam151990.lld.coupon.service;

import com.shivam151990.lld.coupon.model.Product;

import java.util.List;

public class BuyXGetYCouponDiscount implements CouponDiscount {

    private final String productName;
    private final int requiredQuantity;
    private final double amountOff;

    public BuyXGetYCouponDiscount(String productName, int requiredQuantity, double amountOff) {
        this.productName = productName;
        this.requiredQuantity = requiredQuantity;
        this.amountOff = amountOff;
    }

    @Override
    public double discount(List<Product> basket) {
        long matchingQuantity = basket.stream().filter(p -> p.getName().equals(productName)).count();
        return matchingQuantity >= requiredQuantity ? amountOff : 0;
    }
}
