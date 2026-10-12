package com.shivam151990.lld.parking_lot_simple.repo;

import com.shivam151990.lld.parking_lot_simple.model.ParkingSpot;
import com.shivam151990.lld.parking_lot_simple.model.ParkingTicket;
import com.shivam151990.lld.parking_lot_simple.model.Vehicle;

import java.util.List;

public interface ParkingRepo {

    public void addParkingSpot(ParkingSpot spot);

    List<ParkingSpot> getAvailableSpots();

    void save(ParkingTicket ticket);

    ParkingTicket get(Vehicle vehicle);

    void remove(Vehicle vehicle);
}
