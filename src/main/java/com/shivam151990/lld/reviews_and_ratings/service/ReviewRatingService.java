package com.shivam151990.lld.reviews_and_ratings.service;

import com.shivam151990.lld.reviews_and_ratings.exception.ProductRatingException;
import com.shivam151990.lld.reviews_and_ratings.exception.RatingExistsForUserException;
import com.shivam151990.lld.reviews_and_ratings.model.ProductRating;
import com.shivam151990.lld.reviews_and_ratings.model.Rating;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewRatingService {

    private final Map<String, Map<String, ProductRating>> productUserRating;

    public ReviewRatingService() {
        this.productUserRating = new HashMap<>();
    }

    public void addReview(String userId, String productId, Rating rating, String review) {
        if (productUserRating.containsKey(productId) && productUserRating.get(productId).containsKey(userId)) {
            throw new RatingExistsForUserException("Review already exists for the user for the product");
        }

        ProductRating productRating = new ProductRating(productId, rating, review);
        productUserRating.computeIfAbsent(productId, m -> new HashMap<>());
        productUserRating.get(productId).put(userId, productRating);
    }

    public double getAverageRating(String productId) {
        if (!productUserRating.containsKey(productId)) {
            throw new ProductRatingException("Product Rating does not exist");
        }
        return productUserRating.get(productId)
                .values()
                .stream()
                .mapToInt(r -> r.getRating().getValue())
                .average()
                .orElse(0.0);
    }

    public List<String> getTopProducts(int n) {
        return productUserRating.keySet()
                .stream()
                .filter(productId -> productUserRating.get(productId).size() >= 3)
                .sorted(Comparator.comparingDouble(this::getAverageRating).reversed())
                .limit(n)
                .toList();
    }

    public void updateRating(String product, String user, Rating newRating) {
        if (!productUserRating.containsKey(product) || !productUserRating.get(product).containsKey(user)) {
            throw new ProductRatingException("The product rating does not exist");
        }
        productUserRating.get(product).get(user).setRating(newRating);
    }

    public void deleteRating(String product, String user) {
        if (!productUserRating.containsKey(product) || !productUserRating.get(product).containsKey(user)) {
            throw new ProductRatingException("The product rating does not exist");
        }
        productUserRating.get(product).remove(user);
        if (productUserRating.get(product).isEmpty()) {
            productUserRating.remove(product);
        }
    }

    public List<ProductRating> getReviews(String productId) {
        return productUserRating.getOrDefault(productId, Map.of())
                .values()
                .stream()
                .sorted(ProductOrder.ORDER_BY_RECENT_TIME)
                .toList();
    }
}
