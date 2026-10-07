package com.shivam151990.lld.train_booking.model;

import java.util.Objects;

public class BookingRequest {

    private final Train train;
    private final String userId;
    private final String source;
    private final String destination;
    private final ClassCategory category;
    private final int seatCount;

    private BookingRequest(Builder builder) {
        this.train = builder.train;
        this.userId = builder.userId;
        this.source = builder.source;
        this.destination = builder.destination;
        this.category = builder.category;
        this.seatCount = builder.seatCount;
    }

    public static Builder builder(Train train, String userId) {
        return new Builder(train, userId);
    }

    public Train getTrain() { return train; }
    public String getUserId() { return userId; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public ClassCategory getCategory() { return category; }
    public int getSeatCount() { return seatCount; }

    public static class Builder {
        private final Train train;
        private final String userId;
        private String source;
        private String destination;
        private ClassCategory category;
        private int seatCount;

        private Builder(Train train, String userId) {
            this.train = Objects.requireNonNull(train, "train is required");
            this.userId = Objects.requireNonNull(userId, "userId is required");
        }

        public Builder journey(String source, String destination) {
            this.source = Objects.requireNonNull(source, "source is required");
            this.destination = Objects.requireNonNull(destination, "destination is required");
            return this;
        }

        public Builder category(ClassCategory category) {
            this.category = Objects.requireNonNull(category, "category is required");
            return this;
        }

        public Builder seatCount(int seatCount) {
            this.seatCount = seatCount;
            return this;
        }

        public BookingRequest build() {
            if (source == null || destination == null) {
                throw new IllegalStateException("journey(source, destination) is required");
            }
            if (source.equals(destination)) {
                throw new IllegalArgumentException("source and destination must differ");
            }
            if (category == null) {
                throw new IllegalStateException("category is required");
            }
            if (seatCount <= 0) {
                throw new IllegalArgumentException("seatCount must be positive");
            }
            return new BookingRequest(this);
        }
    }
}
