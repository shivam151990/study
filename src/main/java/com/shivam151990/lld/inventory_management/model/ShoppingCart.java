package com.shivam151990.lld.inventory_management.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ShoppingCart {
    private Map<String, Integer> cart;

    public ShoppingCart() {
        cart = new HashMap<>();
    }

    public void addProduct(String productId, int qty) {
        cart.put(productId, qty);
    }
}
