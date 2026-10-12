package com.shivam151990.lld.parking_lot_simple;

import com.shivam151990.lld.parking_lot_simple.exception.ParkingFullException;
import com.shivam151990.lld.parking_lot_simple.model.ParkingSpot;
import com.shivam151990.lld.parking_lot_simple.model.ParkingTicket;
import com.shivam151990.lld.parking_lot_simple.model.Vehicle;
import com.shivam151990.lld.parking_lot_simple.model.VehicleType;
import com.shivam151990.lld.parking_lot_simple.repo.InMemoryParkingRepo;
import com.shivam151990.lld.parking_lot_simple.repo.ParkingRepo;
import com.shivam151990.lld.parking_lot_simple.service.NearestFirst;
import com.shivam151990.lld.parking_lot_simple.service.ParkingService;
import com.shivam151990.lld.parking_lot_simple.service.ParkingStrategy;

import java.time.LocalDateTime;

public class ParkingLotSimpleRunner {

    private static final int FLOORS = 2;
    private static final int MOTORCYCLE_SPOTS = 2;
    private static final int COMPACT_SPOTS = 3;
    private static final int LARGE_SPOTS = 1;
    private static final int RATE_PER_HOUR = 20;

    public static void main(String[] args) {

        // Each floor has 2 Motorcycle, 3 Compact and 1 Large spot
        ParkingRepo parkingRepo = new InMemoryParkingRepo();
        for (int floor = 1; floor <= FLOORS; floor++) {
            int spotNumber = 1;
            for (int i = 0; i < MOTORCYCLE_SPOTS; i++) {
                parkingRepo.addParkingSpot(new ParkingSpot(floor, spotNumber++, VehicleType.MOTORCYCLE));
            }
            for (int i = 0; i < COMPACT_SPOTS; i++) {
                parkingRepo.addParkingSpot(new ParkingSpot(floor, spotNumber++, VehicleType.COMPACT));
            }
            for (int i = 0; i < LARGE_SPOTS; i++) {
                parkingRepo.addParkingSpot(new ParkingSpot(floor, spotNumber++, VehicleType.LARGE));
            }
        }

        ParkingStrategy parkingStrategy = new NearestFirst();
        ParkingService parkingService = new ParkingService(parkingRepo, parkingStrategy, RATE_PER_HOUR);

        Vehicle car1 = new Vehicle("CAR-1", VehicleType.COMPACT);
        Vehicle bike1 = new Vehicle("BIKE-1", VehicleType.MOTORCYCLE);
        Vehicle truck1 = new Vehicle("TRUCK-1", VehicleType.LARGE);
        Vehicle truck2 = new Vehicle("TRUCK-2", VehicleType.LARGE);
        Vehicle truck3 = new Vehicle("TRUCK-3", VehicleType.LARGE);

        LocalDateTime tenAm = LocalDateTime.of(2026, 1, 1, 10, 0);

        // Park vehicles
        park(parkingService, car1, tenAm);
        park(parkingService, bike1, tenAm);
        park(parkingService, truck1, tenAm);
        park(parkingService, truck2, tenAm);

        // Only 2 large spots exist, so this one should be rejected
        park(parkingService, truck3, tenAm);

        // Car stays 10:00 to 12:30, charged for 3 hours (rounded up)
        System.out.println("Fee for " + car1.getId() + ": " + parkingService.exit(car1, tenAm.plusMinutes(150)));

        // Exactly 2 hours is charged for 2 hours
        System.out.println("Fee for " + truck1.getId() + ": " + parkingService.exit(truck1, tenAm.plusHours(2)));

        // The freed large spot can be reused
        park(parkingService, truck3, tenAm.plusHours(2));
        System.out.println("Fee for " + truck3.getId() + ": "
                + parkingService.exit(truck3, tenAm.plusHours(2).plusMinutes(1)));

        // Ticket was removed on exit, so a second exit charges nothing
        System.out.println("Second exit for " + truck3.getId() + ": "
                + parkingService.exit(truck3, tenAm.plusHours(5)));
    }

    private static void park(ParkingService parkingService, Vehicle vehicle, LocalDateTime entryTime) {
        try {
            ParkingTicket ticket = parkingService.park(vehicle, entryTime);
            ParkingSpot spot = ticket.getSpot();
            System.out.println(vehicle.getId() + " parked at floor " + spot.getFloor()
                    + ", spot " + spot.getSpotNumber() + " (" + spot.getType() + ")");
        } catch (ParkingFullException e) {
            System.out.println(vehicle.getId() + " rejected: " + e.getMessage());
        }
    }
}
