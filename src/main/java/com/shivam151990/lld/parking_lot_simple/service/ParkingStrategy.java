package com.shivam151990.lld.parking_lot_simple.service;

import com.shivam151990.lld.parking_lot_simple.model.ParkingSpot;
import com.shivam151990.lld.parking_lot_simple.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface ParkingStrategy {
    Optional<ParkingSpot> getSpot(Vehicle vehicle, List<ParkingSpot> spots);
}
