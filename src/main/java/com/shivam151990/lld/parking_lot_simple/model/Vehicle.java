package com.shivam151990.lld.parking_lot_simple.model;

import lombok.Getter;

@Getter
public class Vehicle {

    private String id;
    private VehicleType type;

    public Vehicle(String id, VehicleType type) {
        this.id = id;
        this.type = type;
    }
}
