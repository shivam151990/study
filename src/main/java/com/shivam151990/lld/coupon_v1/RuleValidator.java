package com.shivam151990.lld.coupon_v1;

import com.shivam151990.lld.coupon_v1.model.Coupon;

import java.time.LocalDateTime;

public interface RuleValidator {

    /** Throws {@link CouponRuleProcessException} with the reason if the coupon cannot be applied. */
    void canProcess(Coupon coupon, long basketTotal, LocalDateTime now);

    default boolean isApplicable(Coupon coupon, long basketTotal, LocalDateTime now) {
        try {
            canProcess(coupon, basketTotal, now);
            return true;
        } catch (CouponRuleProcessException e) {
            return false;
        }
    }
}
