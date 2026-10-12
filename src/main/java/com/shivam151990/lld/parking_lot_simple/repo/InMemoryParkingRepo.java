package com.shivam151990.lld.parking_lot_simple.repo;

import com.shivam151990.lld.parking_lot_simple.model.ParkingSpot;
import com.shivam151990.lld.parking_lot_simple.model.ParkingTicket;
import com.shivam151990.lld.parking_lot_simple.model.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryParkingRepo implements ParkingRepo {

    private List<ParkingSpot> spots;
    private Map<String, ParkingTicket> tickets;

    public InMemoryParkingRepo() {
        spots = new ArrayList<>();
        tickets = new HashMap<>();
    }

    @Override
    public void addParkingSpot(ParkingSpot spot) {
        spots.add(spot);
    }

    @Override
    public List<ParkingSpot> getAvailableSpots() {
        return spots.stream().filter(s -> !s.isOccupied()).toList();
    }

    @Override
    public void save(ParkingTicket ticket) {
        if (tickets.containsKey(ticket.getVehicleId())) {
            System.out.println("Ticket already exists");
            return;
        }
        tickets.put(ticket.getVehicleId(), ticket);
    }

    @Override
    public ParkingTicket get(Vehicle vehicle) {
        return tickets.get(vehicle.getId());
    }

    @Override
    public void remove(Vehicle vehicle) {
        tickets.remove(vehicle.getId());
    }
}
