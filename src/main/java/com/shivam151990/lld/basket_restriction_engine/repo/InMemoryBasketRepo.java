package com.shivam151990.lld.basket_restriction_engine.repo;

import java.util.HashMap;
import java.util.Map;

/** Keeps basket counts in memory; also used by validate() for temporary per-basket totals. */
public class InMemoryBasketRepo implements BasketRepo {

    private final Map<String, Integer> productCount = new HashMap<>();
    private final Map<String, Integer> categoryCount = new HashMap<>();

    @Override
    public void addProduct(String id, int qty, String category) {
        productCount.put(id, productCount.getOrDefault(id, 0) + qty);
        categoryCount.put(category, categoryCount.getOrDefault(category, 0) + qty);
    }

    @Override
    public int getProductCount(String id) {
        return productCount.getOrDefault(id, 0);
    }

    @Override
    public int getCategoryCount(String category) {
        return categoryCount.getOrDefault(category, 0);
    }
}
