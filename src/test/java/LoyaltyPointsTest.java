import com.shivam151990.lld.loyalty_points.repo.InMemLoyaltyPointsRepo;
import com.shivam151990.lld.loyalty_points.service.LoyaltyPointService;
import com.shivam151990.lld.loyalty_points.model.LoyaltyPoints;
import com.shivam151990.lld.loyalty_points.exception.LoyaltyPointsRedemptionException;
import com.shivam151990.lld.loyalty_points.repo.LoyaltyPointsRepo;
import com.shivam151990.lld.loyalty_points.service.PointsForCategory;
import com.shivam151990.lld.loyalty_points.model.PurchaseCategory;
import com.shivam151990.lld.loyalty_points.model.UserHistory;
import com.shivam151990.lld.loyalty_points.model.UserHistory.Op;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoyaltyPointsTest {

    private static final String USER = "u1";

    private LocalDateTime now;
    private LoyaltyPointsRepo repo;
    private LoyaltyPointService service;

    @BeforeEach
    public void before() {
        now = LocalDateTime.now();
        repo = new InMemLoyaltyPointsRepo();
        // Produce gives 2 points per ₹100
        service = new LoyaltyPointService(new PointsForCategory(Map.of(PurchaseCategory.PRODUCE, 2), repo), repo);
    }

    // ---- 1. earn ----

    @Test
    public void earnAtDefaultRate() {
        LoyaltyPointService defaultRate = new LoyaltyPointService(new PointsForCategory(Map.of(), repo), repo);
        defaultRate.earn(USER, 550, PurchaseCategory.PRODUCE);
        assertEquals(5, defaultRate.getBalance(USER, now));
    }

    @Test
    public void earnAtCategoryOverrideRate() {
        service.earn(USER, 550, PurchaseCategory.PRODUCE);
        assertEquals(10, service.getBalance(USER, now));
    }

    @Test
    public void earnAddsUp() {
        service.earn(USER, 500, PurchaseCategory.PRODUCE);
        service.earn(USER, 300, PurchaseCategory.PRODUCE);
        assertEquals(16, service.getBalance(USER, now));
    }

    // ---- 2. redeem ----

    @Test
    public void redeemDeductsPoints() {
        service.earn(USER, 500, PurchaseCategory.PRODUCE);
        service.redeem(USER, 4);
        assertEquals(6, service.getBalance(USER, now));
    }

    @Test
    public void redeemFailsWhenBalanceTooLow() {
        service.earn(USER, 500, PurchaseCategory.PRODUCE);
        assertThrows(LoyaltyPointsRedemptionException.class, () -> service.redeem(USER, 11));
        assertEquals(10, service.getBalance(USER, now));
    }

    @Test
    public void redeemFailsForUnknownUser() {
        assertThrows(LoyaltyPointsRedemptionException.class, () -> service.redeem("nobody", 1));
    }

    // ---- 3. expiry and oldest-first ----

    @Test
    public void pointsExpireAfter12Months() {
        service.earn(USER, 500, PurchaseCategory.PRODUCE);
        assertEquals(10, service.getBalance(USER, now.plusMonths(11)));
        assertEquals(0, service.getBalance(USER, now.plusMonths(12)));
    }

    @Test
    public void pointsDoNotExpireAfter12Days() {
        service.earn(USER, 500, PurchaseCategory.PRODUCE);
        assertEquals(10, service.getBalance(USER, now.plusDays(13)));
    }

    @Test
    public void redeemCannotUseExpiredPoints() {
        repo.addPoints(USER, new LoyaltyPoints(10, now.minusMonths(13)));
        service.earn(USER, 250, PurchaseCategory.PRODUCE);  // 4 points
        assertThrows(LoyaltyPointsRedemptionException.class, () -> service.redeem(USER, 5));
        assertEquals(4, service.getBalance(USER, now));
    }

    @Test
    public void redeemUsesOldestPointsFirst() {
        repo.addPoints(USER, new LoyaltyPoints(10, now.minusMonths(6)));
        repo.addPoints(USER, new LoyaltyPoints(10, now.minusMonths(1)));
        service.redeem(USER, 10);  // should use up the older batch

        // When the older batch reaches 12 months, nothing is lost because it was already spent
        assertEquals(10, service.getBalance(USER, now.plusMonths(7)));
        // The newer batch expires 12 months after it was earned
        assertEquals(0, service.getBalance(USER, now.plusMonths(11)));
    }

    @Test
    public void redeemSpansMultipleBatches() {
        repo.addPoints(USER, new LoyaltyPoints(5, now.minusMonths(6)));
        repo.addPoints(USER, new LoyaltyPoints(10, now.minusMonths(1)));
        service.redeem(USER, 8);  // 5 from the older batch + 3 from the newer one

        assertEquals(7, service.getBalance(USER, now));
        assertEquals(7, service.getBalance(USER, now.plusMonths(7)));  // older batch already empty
    }

    // ---- 4. getBalance(userId, now) ----

    @Test
    public void balanceForUnknownUserThrows() {
        assertThrows(LoyaltyPointsRedemptionException.class, () -> service.getBalance("nobody", now));
    }

    // ---- 5. history ----

    @Test
    public void historyListsEarnAndRedeemInOrder() {
        service.earn(USER, 500, PurchaseCategory.PRODUCE);
        service.redeem(USER, 3);
        service.earn(USER, 200, PurchaseCategory.PRODUCE);

        List<UserHistory> history = service.getHistory(USER);
        assertEquals(List.of(Op.EARNED, Op.REDEEMED, Op.EARNED), history.stream().map(UserHistory::getOperation).toList());
        assertEquals(List.of(10, 3, 4), history.stream().map(UserHistory::getPoints).toList());
    }

    @Test
    public void failedRedeemIsNotInHistory() {
        service.earn(USER, 500, PurchaseCategory.PRODUCE);
        assertThrows(LoyaltyPointsRedemptionException.class, () -> service.redeem(USER, 50));
        assertEquals(1, service.getHistory(USER).size());
    }

    @Test
    public void historyForUnknownUserIsEmpty() {
        assertTrue(service.getHistory("nobody").isEmpty());
    }
}
