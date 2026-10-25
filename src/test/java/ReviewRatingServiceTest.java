import com.shivam151990.lld.reviews_and_ratings.exception.ProductRatingException;
import com.shivam151990.lld.reviews_and_ratings.exception.RatingExistsForUserException;
import com.shivam151990.lld.reviews_and_ratings.model.ProductRating;
import com.shivam151990.lld.reviews_and_ratings.model.Rating;
import com.shivam151990.lld.reviews_and_ratings.service.ReviewRatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReviewRatingServiceTest {

    private ReviewRatingService service;

    @BeforeEach
    public void before() {
        service = new ReviewRatingService();
    }

    // ---- 1. addReview ----

    @Test
    public void addReviewStoresRatingAndText() {
        service.addReview("u1", "p1", Rating.FOUR, "good");
        ProductRating review = service.getReviews("p1").get(0);
        assertEquals(Rating.FOUR, review.getRating());
        assertEquals("good", review.getReview());
        assertEquals("p1", review.getProductId());
    }

    @Test
    public void userCanReviewProductOnlyOnce() {
        service.addReview("u1", "p1", Rating.FOUR, "good");
        assertThrows(RatingExistsForUserException.class, () -> service.addReview("u1", "p1", Rating.ONE, "changed my mind"));
        assertEquals(4.0, service.getAverageRating("p1"));
    }

    @Test
    public void userCanReviewDifferentProducts() {
        service.addReview("u1", "p1", Rating.FOUR, "good");
        assertDoesNotThrow(() -> service.addReview("u1", "p2", Rating.TWO, "meh"));
    }

    // ---- 2. getAverageRating ----

    @Test
    public void averageRating() {
        service.addReview("u1", "p1", Rating.FIVE, "");
        service.addReview("u2", "p1", Rating.FOUR, "");
        service.addReview("u3", "p1", Rating.ONE, "");
        assertEquals(10.0 / 3, service.getAverageRating("p1"), 1e-9);
    }

    @Test
    public void averageRatingForUnknownProductFails() {
        assertThrows(ProductRatingException.class, () -> service.getAverageRating("nope"));
    }

    // ---- 3. getTopProducts ----

    @Test
    public void topProductsOrderedByAverage() {
        addReviews("p1", Rating.THREE, Rating.THREE, Rating.THREE);   // 3.0
        addReviews("p2", Rating.FIVE, Rating.FIVE, Rating.FOUR);      // 4.67
        addReviews("p3", Rating.FOUR, Rating.FOUR, Rating.FOUR);      // 4.0
        assertEquals(List.of("p2", "p3", "p1"), service.getTopProducts(3));
        assertEquals(List.of("p2", "p3"), service.getTopProducts(2));
    }

    @Test
    public void topProductsRequireAtLeastThreeReviews() {
        addReviews("p1", Rating.FIVE, Rating.FIVE);                    // only 2 reviews
        addReviews("p2", Rating.TWO, Rating.TWO, Rating.TWO);
        assertEquals(List.of("p2"), service.getTopProducts(5));
    }

    @Test
    public void topProductsListsEachProductOnce() {
        addReviews("p1", Rating.FIVE, Rating.FIVE, Rating.FIVE, Rating.FIVE);
        assertEquals(List.of("p1"), service.getTopProducts(3));
    }

    @Test
    public void topProductsEmptyWhenNoneQualify() {
        addReviews("p1", Rating.FIVE);
        assertTrue(service.getTopProducts(3).isEmpty());
    }

    // ---- 4. updateRating / deleteRating ----

    @Test
    public void updateRatingChangesAverage() {
        service.addReview("u1", "p1", Rating.FIVE, "");
        service.addReview("u2", "p1", Rating.ONE, "");
        service.updateRating("p1", "u2", Rating.THREE);
        assertEquals(4.0, service.getAverageRating("p1"));
    }

    @Test
    public void updateMissingRatingFails() {
        service.addReview("u1", "p1", Rating.FIVE, "");
        assertThrows(ProductRatingException.class, () -> service.updateRating("p1", "u2", Rating.ONE));
        assertThrows(ProductRatingException.class, () -> service.updateRating("nope", "u1", Rating.ONE));
    }

    @Test
    public void deleteRatingChangesAverage() {
        service.addReview("u1", "p1", Rating.FIVE, "");
        service.addReview("u2", "p1", Rating.ONE, "");
        service.deleteRating("p1", "u2");
        assertEquals(5.0, service.getAverageRating("p1"));
    }

    @Test
    public void deletingLastReviewRemovesProduct() {
        service.addReview("u1", "p1", Rating.FIVE, "");
        service.deleteRating("p1", "u1");
        assertThrows(ProductRatingException.class, () -> service.getAverageRating("p1"));
        assertTrue(service.getReviews("p1").isEmpty());
    }

    @Test
    public void deleteMissingRatingFails() {
        assertThrows(ProductRatingException.class, () -> service.deleteRating("p1", "u1"));
    }

    @Test
    public void userCanReviewAgainAfterDelete() {
        service.addReview("u1", "p1", Rating.FIVE, "");
        service.deleteRating("p1", "u1");
        assertDoesNotThrow(() -> service.addReview("u1", "p1", Rating.TWO, ""));
    }

    @Test
    public void deleteDropsProductBelowTopThreshold() {
        addReviews("p1", Rating.FIVE, Rating.FIVE, Rating.FIVE);
        service.deleteRating("p1", "u0");
        assertTrue(service.getTopProducts(3).isEmpty());
    }

    @Test
    public void updateChangesTopProductsOrder() {
        addReviews("p1", Rating.FIVE, Rating.FIVE, Rating.FIVE);
        addReviews("p2", Rating.FOUR, Rating.FOUR, Rating.FOUR);
        service.updateRating("p1", "u0", Rating.ONE);   // p1 -> 3.67, below p2
        assertEquals(List.of("p2", "p1"), service.getTopProducts(2));
    }

    // ---- 5. getReviews newest first ----

    @Test
    public void reviewsNewestFirst() {
        LocalDateTime base = LocalDateTime.of(2026, 1, 1, 10, 0);
        service.addReview("u1", "p1", Rating.ONE, "first");
        service.addReview("u2", "p1", Rating.TWO, "second");
        service.addReview("u3", "p1", Rating.THREE, "third");
        // Set times explicitly so the order doesn't depend on clock precision
        for (ProductRating r : service.getReviews("p1")) {
            int offset = switch (r.getReview()) {
                case "first" -> 0;
                case "second" -> 1;
                default -> 2;
            };
            r.setCreateTime(base.plusMinutes(offset));
        }
        assertEquals(List.of("third", "second", "first"),
                service.getReviews("p1").stream().map(ProductRating::getReview).toList());
    }

    @Test
    public void reviewsOnlyForRequestedProduct() {
        service.addReview("u1", "p1", Rating.ONE, "for p1");
        service.addReview("u1", "p2", Rating.TWO, "for p2");
        assertEquals(List.of("for p1"), service.getReviews("p1").stream().map(ProductRating::getReview).toList());
    }

    @Test
    public void reviewsForUnknownProductIsEmpty() {
        assertTrue(service.getReviews("nope").isEmpty());
    }

    /** Adds one review per rating, from users u0, u1, u2, ... */
    private void addReviews(String productId, Rating... ratings) {
        for (int i = 0; i < ratings.length; i++) {
            service.addReview("u" + i, productId, ratings[i], "");
        }
    }
}
