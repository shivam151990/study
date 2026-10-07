package com.shivam151990.lld.train_booking.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Booking {

    private final String bookingId;
    private final int trainId;
    private final String userId;
    private final String source;
    private final String destination;
    private final ClassCategory category;
    private final List<Seat> seats;
    private final LocalDateTime bookedAt;
    private BookingStatus status;

    private Booking(Builder builder) {
        this.bookingId = builder.bookingId;
        this.trainId = builder.trainId;
        this.userId = builder.userId;
        this.source = builder.source;
        this.destination = builder.destination;
        this.category = builder.category;
        this.seats = List.copyOf(builder.seats);
        this.bookedAt = LocalDateTime.now();
        this.status = BookingStatus.CONFIRMED;
    }

    public static Builder builder(String bookingId, int trainId, String userId) {
        return new Builder(bookingId, trainId, userId);
    }

    public void markCancelled() {
        this.status = BookingStatus.CANCELLED;
    }

    public static class Builder {
        private final String bookingId;
        private final int trainId;
        private final String userId;
        private String source;
        private String destination;
        private ClassCategory category;
        private List<Seat> seats;

        private Builder(String bookingId, int trainId, String userId) {
            this.bookingId = bookingId;
            this.trainId = trainId;
            this.userId = userId;
        }

        public Builder route(String source, String destination) {
            this.source = source;
            this.destination = destination;
            return this;
        }

        public Builder category(ClassCategory category) {
            this.category = category;
            return this;
        }

        public Builder seats(List<Seat> seats) {
            this.seats = seats;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }
}
