package com.shivam151990.lld.food_ordering.model;

import java.util.HashMap;
import java.util.Map;

public class Restaurant {

    private String id;
    private Map<String, Integer> items;
    private int maxOrders;

    public Restaurant(String id, Map<String, Integer> items, int maxOrders) {
        this.id = id;
        this.items = new HashMap<>(items);
        this.maxOrders = maxOrders;
    }

    public String getId() {
        return id;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public int getMaxOrders() {
        return maxOrders;
    }
}
