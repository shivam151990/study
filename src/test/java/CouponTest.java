import com.shivam151990.lld.coupon_v1.CouponProcessor;
import com.shivam151990.lld.coupon_v1.CouponRuleProcessException;
import com.shivam151990.lld.coupon_v1.CouponRuleProcessException.Reason;
import com.shivam151990.lld.coupon_v1.CouponRuleValidator;
import com.shivam151990.lld.coupon_v1.model.FlatCoupon;
import com.shivam151990.lld.coupon_v1.model.PercentCoupon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CouponTest {

    private final LocalDateTime now = LocalDateTime.of(2026, 1, 15, 12, 0);
    private CouponProcessor processor;

    @BeforeEach
    public void before() {
        processor = new CouponProcessor(new CouponRuleValidator());
        processor.createCoupon(new FlatCoupon("FLAT500", 500, 5000, now.minusDays(10), now.plusDays(10), 1));
        processor.createCoupon(new PercentCoupon("PCT10", 10, 5000, now.minusDays(10), now.plusDays(10), 5, 800));
    }

    @Test
    public void flatCouponDiscountsBasket() {
        assertEquals(5500, processor.apply("FLAT500", 6000, now));
    }

    @Test
    public void percentCouponDiscountsBasket() {
        assertEquals(5400, processor.apply("PCT10", 6000, now));
    }

    @Test
    public void percentCouponDiscountIsCapped() {
        // 10% of 20000 = 2000, capped at 800
        assertEquals(19200, processor.apply("PCT10", 20000, now));
    }

    @Test
    public void percentCouponWithoutCap() {
        processor.createCoupon(new PercentCoupon("PCT50", 50, 0, now.minusDays(1), now.plusDays(1), 1));
        assertEquals(10000, processor.apply("PCT50", 20000, now));
    }

    @Test
    public void flatCouponNeverMakesTotalNegative() {
        processor.createCoupon(new FlatCoupon("BIG", 1000, 0, now.minusDays(1), now.plusDays(1), 1));
        assertEquals(0, processor.apply("BIG", 300, now));
    }

    @Test
    public void unknownCode() {
        assertReason(Reason.UNKNOWN_CODE, () -> processor.apply("NOPE", 6000, now));
    }

    @Test
    public void notStarted() {
        assertReason(Reason.NOT_STARTED, () -> processor.apply("FLAT500", 6000, now.minusDays(11)));
    }

    @Test
    public void expired() {
        assertReason(Reason.EXPIRED, () -> processor.apply("FLAT500", 6000, now.plusDays(11)));
    }

    @Test
    public void belowMinimum() {
        assertReason(Reason.BELOW_MINIMUM, () -> processor.apply("FLAT500", 4999, now));
    }

    @Test
    public void usageLimitReached() {
        processor.apply("FLAT500", 6000, now);
        assertReason(Reason.USAGE_LIMIT_REACHED, () -> processor.apply("FLAT500", 6000, now));
    }

    @Test
    public void failedApplyDoesNotConsumeUsage() {
        assertReason(Reason.BELOW_MINIMUM, () -> processor.apply("FLAT500", 100, now));
        assertEquals(5500, processor.apply("FLAT500", 6000, now));
    }

    @Test
    public void bestCouponPicksHighestDiscount() {
        // basket 6000: FLAT500 -> 500, PCT10 -> 600
        assertEquals(Optional.of("PCT10"), processor.getBestCoupon(6000, now));
        // basket 20000: FLAT500 -> 500, PCT10 -> 800 (capped)
        assertEquals(Optional.of("PCT10"), processor.getBestCoupon(20000, now));
    }

    @Test
    public void bestCouponSkipsInvalidCoupons() {
        processor.createCoupon(new FlatCoupon("SMALL", 100, 0, now.minusDays(1), now.plusDays(1), 1));
        assertEquals(Optional.of("SMALL"), processor.getBestCoupon(1000, now));
        assertEquals(Optional.empty(), processor.getBestCoupon(1000, now.plusDays(30)));
    }

    @Test
    public void bestCouponDoesNotConsumeUsage() {
        processor.getBestCoupon(6000, now);
        assertEquals(5500, processor.apply("FLAT500", 6000, now));
    }

    @Test
    public void duplicateCodeRejected() {
        assertThrows(IllegalArgumentException.class, () ->
                processor.createCoupon(new FlatCoupon("FLAT500", 100, 0, now, now.plusDays(1), 1)));
    }

    private void assertReason(Reason expected, Executable action) {
        CouponRuleProcessException e = assertThrows(CouponRuleProcessException.class, action);
        assertEquals(expected, e.getReason());
    }
}
