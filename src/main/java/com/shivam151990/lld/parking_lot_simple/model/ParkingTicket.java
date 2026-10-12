package com.shivam151990.lld.parking_lot_simple.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ParkingTicket {

    private String vehicleId;
    private ParkingSpot spot;
    private LocalDateTime createTime;

    public ParkingTicket(String vehicleId, ParkingSpot spot) {
        this(vehicleId, spot, LocalDateTime.now());
    }

    public ParkingTicket(String vehicleId, ParkingSpot spot, LocalDateTime createTime) {
        this.createTime = createTime;
        this.spot = spot;
        this.vehicleId = vehicleId;
    }
}
