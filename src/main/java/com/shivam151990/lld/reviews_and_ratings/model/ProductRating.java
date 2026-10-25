package com.shivam151990.lld.reviews_and_ratings.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ProductRating {
    @Getter
    private final String productId;

    @Setter
    @Getter
    private Rating rating;

    @Setter
    @Getter
    private String review;

    @Getter
    @Setter
    private LocalDateTime createTime;

    public ProductRating(String productId) {
        this.productId = productId;
        this.createTime = LocalDateTime.now();
    }

    public ProductRating(String productId, Rating rating, String review) {
        this.productId = productId;
        this.rating = rating;
        this.review = review;
        this.createTime = LocalDateTime.now();
    }
}
