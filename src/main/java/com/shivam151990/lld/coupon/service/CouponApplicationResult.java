package com.shivam151990.lld.coupon.service;

import java.util.Optional;

public record CouponApplicationResult(Optional<String> appliedCouponCode, double finalPrice) {
}
