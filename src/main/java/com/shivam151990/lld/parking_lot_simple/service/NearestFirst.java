package com.shivam151990.lld.parking_lot_simple.service;

import com.shivam151990.lld.parking_lot_simple.model.ParkingSpot;
import com.shivam151990.lld.parking_lot_simple.model.Vehicle;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class NearestFirst implements ParkingStrategy {

    private final Comparator<ParkingSpot> nearestComparator = Comparator.comparingInt(ParkingSpot::getFloor)
            .thenComparingInt(ParkingSpot::getSpotNumber);

    @Override
    public Optional<ParkingSpot> getSpot(Vehicle vehicle, List<ParkingSpot> spots) {
        if (spots.isEmpty()) {
            return Optional.empty();
        }
        return spots.stream()
                .filter(s -> s.getType() == vehicle.getType())
                .min(nearestComparator);
    }
}
