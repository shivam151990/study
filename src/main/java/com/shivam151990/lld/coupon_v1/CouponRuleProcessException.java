package com.shivam151990.lld.coupon_v1;

import lombok.Getter;

@Getter
public class CouponRuleProcessException extends RuntimeException {

    public enum Reason {
        UNKNOWN_CODE,
        NOT_STARTED,
        EXPIRED,
        BELOW_MINIMUM,
        USAGE_LIMIT_REACHED
    }

    private final Reason reason;

    public CouponRuleProcessException(Reason reason, String message) {
        super(message);
        this.reason = reason;
    }
}
