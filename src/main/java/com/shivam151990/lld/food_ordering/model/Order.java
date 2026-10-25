package com.shivam151990.lld.food_ordering.model;

import lombok.Getter;

import java.util.Map;

@Getter
public class Order {

    private String orderId;
    private Map<String, Integer> items;

    public Order(String orderId, Map<String, Integer> items) {
        this.orderId = orderId;
        this.items = items;
    }
}
