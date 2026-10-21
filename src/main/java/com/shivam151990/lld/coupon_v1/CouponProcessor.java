package com.shivam151990.lld.coupon_v1;

import com.shivam151990.lld.coupon_v1.CouponRuleProcessException.Reason;
import com.shivam151990.lld.coupon_v1.model.Coupon;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class CouponProcessor {

    private final RuleValidator validator;
    private final Map<String, Coupon> coupons = new LinkedHashMap<>();

    public CouponProcessor(RuleValidator validator) {
        this.validator = validator;
    }

    public synchronized void createCoupon(Coupon coupon) {
        if (coupons.putIfAbsent(coupon.getCode(), coupon) != null) {
            throw new IllegalArgumentException("Coupon " + coupon.getCode() + " already exists");
        }
    }

    /** Returns the discounted basket total, or throws {@link CouponRuleProcessException} with the reason. */
    public synchronized long apply(String code, long basketTotal, LocalDateTime now) {
        Coupon coupon = coupons.get(code);
        if (coupon == null) {
            throw new CouponRuleProcessException(Reason.UNKNOWN_CODE, "Unknown coupon code " + code);
        }
        validator.canProcess(coupon, basketTotal, now);
        coupon.incrementUsage();
        return coupon.apply(basketTotal);
    }

    /** Code of the valid coupon giving the highest discount; does not consume usage. */
    public synchronized Optional<String> getBestCoupon(long basketTotal, LocalDateTime now) {
        return coupons.values().stream()
                .filter(coupon -> validator.isApplicable(coupon, basketTotal, now))
                .max(Comparator.comparingLong(coupon -> coupon.getDiscount(basketTotal)))
                .map(Coupon::getCode);
    }
}
