package com.shivam151990.lld.train_booking.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TrainRoute {

    private final Train train;
    private final String source;
    private final String destination;
    private final LocalDateTime departureTime;

    public TrainRoute(Train train, String source, String destination, LocalDateTime departureTime) {
        this.train = train;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
    }

    public boolean matches(String source, String destination) {
        return this.source.equals(source) && this.destination.equals(destination);
    }
}
