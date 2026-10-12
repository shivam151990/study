package com.shivam151990.lld.parking_lot_simple.service;

import com.shivam151990.lld.parking_lot_simple.exception.ParkingFullException;
import com.shivam151990.lld.parking_lot_simple.model.ParkingSpot;
import com.shivam151990.lld.parking_lot_simple.model.ParkingTicket;
import com.shivam151990.lld.parking_lot_simple.model.Vehicle;
import com.shivam151990.lld.parking_lot_simple.repo.ParkingRepo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ParkingService {

    private ParkingRepo parkingRepo;
    private ParkingStrategy parkingStrategy;
    private int ratePerHour;

    public ParkingService(ParkingRepo parkingRepo, ParkingStrategy parkingStrategy, int ratePerHour) {
        this.parkingRepo = parkingRepo;
        this.parkingStrategy = parkingStrategy;
        this.ratePerHour = ratePerHour;
    }

    public ParkingTicket park(Vehicle vehicle) {
        return park(vehicle, LocalDateTime.now());
    }

    public ParkingTicket park(Vehicle vehicle, LocalDateTime entryTime) {
        //Check for parking spots
        List<ParkingSpot> availableSpots = parkingRepo.getAvailableSpots();
        Optional<ParkingSpot> optSpot = parkingStrategy.getSpot(vehicle, availableSpots);
        if (optSpot.isEmpty()) {
            throw new ParkingFullException("No parking space");
        }
        ParkingSpot spot = optSpot.get();
        spot.occupySpot();

        // Park Vehicle and get ticket
        ParkingTicket ticket = new ParkingTicket(vehicle.getId(), spot, entryTime);
        parkingRepo.save(ticket);
        return ticket;
    }

    public double exit(Vehicle vehicle) {
        return exit(vehicle, LocalDateTime.now());
    }

    public double exit(Vehicle vehicle, LocalDateTime exitTime) {
        ParkingTicket ticket = parkingRepo.get(vehicle);
        if (ticket == null) {
            return 0.0;
        }
        Duration stay = Duration.between(ticket.getCreateTime(), exitTime);
        long hrs = stay.toHours();
        // Round up any partial hour
        if (!stay.minusHours(hrs).isZero()) {
            hrs++;
        }
        ticket.getSpot().freeSpot();
        parkingRepo.remove(vehicle);
        return hrs * ratePerHour;
    }
}
