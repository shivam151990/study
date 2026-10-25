import com.shivam151990.lld.basket_restriction_engine.exception.ValidationRuleException;
import com.shivam151990.lld.basket_restriction_engine.model.Basket;
import com.shivam151990.lld.basket_restriction_engine.model.ProductQuantity;
import com.shivam151990.lld.basket_restriction_engine.model.Violation;
import com.shivam151990.lld.basket_restriction_engine.repo.BasketRepo;
import com.shivam151990.lld.basket_restriction_engine.repo.InMemoryBasketRepo;
import com.shivam151990.lld.basket_restriction_engine.service.BasketProcessor;
import com.shivam151990.lld.basket_restriction_engine.service.MaxCategoryItemsRule;
import com.shivam151990.lld.basket_restriction_engine.service.MaxProductQtyRule;
import com.shivam151990.lld.basket_restriction_engine.service.RuleFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasketRestrictionTest {

    private static final String PARACETAMOL_LIMIT = "Max quantity 2 exceeded for product paracetamol";
    private static final String MEDICINE_LIMIT = "Max 5 items exceeded for category medicine";

    private BasketRepo repo;
    private BasketProcessor processor;

    @BeforeEach
    public void before() {
        repo = new InMemoryBasketRepo();
        // paracetamol: max 2 per basket; medicine category: max 5 items in total
        List<RuleFilter> rules = List.of(
                new MaxProductQtyRule(Map.of("paracetamol", 2)),
                new MaxCategoryItemsRule(Map.of("medicine", 5)));
        processor = new BasketProcessor(rules, repo);
    }

    // ---- addItem ----

    @Test
    public void addItemWithinLimits() {
        assertDoesNotThrow(() -> processor.addItem("paracetamol", 2, "medicine"));
        assertEquals(2, repo.getProductCount("paracetamol"));
        assertEquals(2, repo.getCategoryCount("medicine"));
    }

    @Test
    public void addItemRejectedWithProductRuleMessage() {
        assertRejected(PARACETAMOL_LIMIT, () -> processor.addItem("paracetamol", 3, "medicine"));
    }

    @Test
    public void productLimitIsCumulativeAcrossAdds() {
        processor.addItem("paracetamol", 1, "medicine");
        processor.addItem("paracetamol", 1, "medicine");
        assertRejected(PARACETAMOL_LIMIT, () -> processor.addItem("paracetamol", 1, "medicine"));
    }

    @Test
    public void addItemRejectedWithCategoryRuleMessage() {
        processor.addItem("ibuprofen", 3, "medicine");
        processor.addItem("aspirin", 2, "medicine");
        assertRejected(MEDICINE_LIMIT, () -> processor.addItem("cough_syrup", 1, "medicine"));
    }

    @Test
    public void categoryLimitAppliesToProductWithItsOwnLimit() {
        processor.addItem("ibuprofen", 4, "medicine");
        // paracetamol is within its own limit of 2, but medicine would reach 6 > 5
        assertRejected(MEDICINE_LIMIT, () -> processor.addItem("paracetamol", 2, "medicine"));
    }

    @Test
    public void rejectedItemIsNotAdded() {
        assertThrows(ValidationRuleException.class, () -> processor.addItem("paracetamol", 3, "medicine"));
        assertEquals(0, repo.getProductCount("paracetamol"));
        assertEquals(0, repo.getCategoryCount("medicine"));
    }

    @Test
    public void unrestrictedItemsAreAllowed() {
        assertDoesNotThrow(() -> processor.addItem("bread", 100, "bakery"));
    }

    // ---- validate ----

    @Test
    public void validateReturnsAllViolations() {
        Basket basket = new Basket(List.of(
                new ProductQuantity("paracetamol", 3, "medicine"),
                new ProductQuantity("ibuprofen", 3, "medicine"),
                new ProductQuantity("bread", 10, "bakery")));
        assertEquals(List.of(PARACETAMOL_LIMIT, MEDICINE_LIMIT), messages(processor.validate(basket)));
    }

    @Test
    public void validateValidBasketHasNoViolations() {
        Basket basket = new Basket(List.of(
                new ProductQuantity("paracetamol", 2, "medicine"),
                new ProductQuantity("bread", 10, "bakery")));
        assertTrue(processor.validate(basket).isEmpty());
    }

    @Test
    public void validateAddsUpRepeatedLinesOfSameProduct() {
        // each line alone is within the limit of 2, together they are 4
        Basket basket = new Basket(List.of(
                new ProductQuantity("paracetamol", 2, "medicine"),
                new ProductQuantity("paracetamol", 2, "medicine")));
        assertEquals(List.of(PARACETAMOL_LIMIT), messages(processor.validate(basket)));
    }

    @Test
    public void validateReportsEachBrokenLimitOnce() {
        Basket basket = new Basket(List.of(
                new ProductQuantity("ibuprofen", 4, "medicine"),
                new ProductQuantity("aspirin", 2, "medicine"),
                new ProductQuantity("cough_syrup", 2, "medicine")));
        assertEquals(List.of(MEDICINE_LIMIT), messages(processor.validate(basket)));
    }

    @Test
    public void validateIgnoresItemsAddedThroughAddItem() {
        processor.addItem("paracetamol", 2, "medicine");
        Basket basket = new Basket(List.of(new ProductQuantity("paracetamol", 1, "medicine")));
        assertTrue(processor.validate(basket).isEmpty());
    }

    // ---- extensibility ----

    @Test
    public void newRuleTypeCanBePluggedInWithoutChangingExistingCode() {
        // e.g. a ban on a category, added purely as a new RuleFilter implementation
        RuleFilter noAlcohol = (pq, counts) -> pq.category().equals("alcohol")
                ? Optional.of("Alcohol is not allowed")
                : Optional.empty();
        BasketProcessor withNewRule = new BasketProcessor(List.of(noAlcohol), new InMemoryBasketRepo());
        assertRejected("Alcohol is not allowed", () -> withNewRule.addItem("beer", 1, "alcohol"));
        assertDoesNotThrow(() -> withNewRule.addItem("bread", 1, "bakery"));
    }

    private static void assertRejected(String expectedMessage, org.junit.jupiter.api.function.Executable action) {
        ValidationRuleException e = assertThrows(ValidationRuleException.class, action);
        assertEquals(expectedMessage, e.getMessage());
    }

    private static List<String> messages(List<Violation> violations) {
        return violations.stream().map(Violation::message).toList();
    }
}
