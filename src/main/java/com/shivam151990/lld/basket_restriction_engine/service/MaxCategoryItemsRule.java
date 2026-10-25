package com.shivam151990.lld.basket_restriction_engine.service;

import com.shivam151990.lld.basket_restriction_engine.model.ProductQuantity;
import com.shivam151990.lld.basket_restriction_engine.repo.BasketRepo;

import java.util.Map;
import java.util.Optional;

/** Limits the total number of items across all products of a category. Categories without a limit are unrestricted. */
public class MaxCategoryItemsRule implements RuleFilter {

    // category -> max total items allowed in the basket
    private final Map<String, Integer> maxItemsPerCategory;

    public MaxCategoryItemsRule(Map<String, Integer> maxItemsPerCategory) {
        this.maxItemsPerCategory = maxItemsPerCategory;
    }

    @Override
    public Optional<String> check(ProductQuantity productQuantity, BasketRepo counts) {
        String category = productQuantity.category();
        Integer max = maxItemsPerCategory.get(category);
        if (max == null) {
            return Optional.empty();
        }
        // Items of this category already in the basket plus the quantity being added
        int newTotal = counts.getCategoryCount(category) + productQuantity.qty();
        if (newTotal > max) {
            return Optional.of("Max " + max + " items exceeded for category " + category);
        }
        return Optional.empty();
    }
}
