package com.shivam151990.lld.coupon_v1;

import com.shivam151990.lld.coupon_v1.CouponRuleProcessException.Reason;
import com.shivam151990.lld.coupon_v1.model.Coupon;

import java.time.LocalDateTime;

public class CouponRuleValidator implements RuleValidator {

    @Override
    public void canProcess(Coupon coupon, long basketTotal, LocalDateTime now) {
        if (now.isBefore(coupon.getStart())) {
            throw new CouponRuleProcessException(Reason.NOT_STARTED, "Coupon " + coupon.getCode() + " is not active yet");
        }
        if (now.isAfter(coupon.getEnd())) {
            throw new CouponRuleProcessException(Reason.EXPIRED, "Coupon " + coupon.getCode() + " has expired");
        }
        if (basketTotal < coupon.getMinBasketValue()) {
            throw new CouponRuleProcessException(Reason.BELOW_MINIMUM,
                    "Basket value is less than minimum " + coupon.getMinBasketValue());
        }
        if (coupon.getUsageCount() >= coupon.getTotalUsageLimit()) {
            throw new CouponRuleProcessException(Reason.USAGE_LIMIT_REACHED, "Coupon " + coupon.getCode() + " usage limit reached");
        }
    }
}
