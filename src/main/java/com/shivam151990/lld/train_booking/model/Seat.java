package com.shivam151990.lld.train_booking.model;

import lombok.Getter;

@Getter
public class Seat {

    private final ClassCategory category;
    private final int number;
    private final int trainId;

    private String bookingId;

    public Seat(ClassCategory category, int number, int trainId) {
        this.category = category;
        this.number = number;
        this.trainId = trainId;
    }

    public synchronized boolean isAvailable() {
        return bookingId == null;
    }

    public synchronized void reserve(String bookingId) {
        if (!isAvailable()) {
            throw new IllegalStateException("Seat " + number + " is already booked");
        }
        this.bookingId = bookingId;
    }

    public synchronized void release(String bookingId) {
        if (bookingId.equals(this.bookingId)) {
            this.bookingId = null;
        }
    }
}
