package com.shivam151990.lld.reviews_and_ratings.service;

import com.shivam151990.lld.reviews_and_ratings.model.ProductRating;

import java.util.Comparator;

public interface ProductOrder {

    Comparator<ProductRating> ORDER_BY_HIGH_RATING = Comparator
            .comparing((ProductRating rating) -> rating.getRating().getValue())
            .reversed();

    Comparator<ProductRating> ORDER_BY_RECENT_TIME = Comparator
            .comparing(ProductRating::getCreateTime)
            .reversed();
}
