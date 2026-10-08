package com.shivam151990.lld.coupon.service;

import com.shivam151990.lld.coupon.model.Product;

import java.util.List;

public interface CouponDiscount {
    double discount(List<Product> basket);
}
