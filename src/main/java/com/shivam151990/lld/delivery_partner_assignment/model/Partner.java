package com.shivam151990.lld.delivery_partner_assignment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Partner {
    private final String id;
    private final double location;
    @Setter
    private boolean available;

    public Partner(String id, double location) {
        this.id = id;
        this.location = location;
        this.available = true;
    }
}
