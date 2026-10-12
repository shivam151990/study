package com.shivam151990.lld.parking_lot_simple.model;

import lombok.Getter;

@Getter
public class ParkingSpot {

    private int floor;
    private int spotNumber;
    private VehicleType type;
    boolean isOccupied;

    public ParkingSpot(int floor, int spotNumber, VehicleType type) {
        this.floor = floor;
        this.spotNumber = spotNumber;
        this.type = type;
        this.isOccupied = false;
    }

    public void occupySpot() {
        isOccupied = true;
    }

    public void freeSpot() {
        isOccupied = false;
    }
}
