package com.shivam151990.lld.basket_restriction_engine.repo;

/** Running quantities in a basket, tracked per product and per category. */
public interface BasketRepo {
    void addProduct(String id, int qty, String category);
    int getProductCount(String id);
    int getCategoryCount(String category);
}
