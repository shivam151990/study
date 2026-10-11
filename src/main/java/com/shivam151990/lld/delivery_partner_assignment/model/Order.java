package com.shivam151990.lld.delivery_partner_assignment.model;

import lombok.Getter;

@Getter
public class Order {
    private final String id;
    private final double location;

    public Order(String id, double location) {
        this.id = id;
        this.location = location;
    }
}
