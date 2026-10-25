package com.shivam151990.lld.loyalty_points.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class LoyaltyPoints {

    @Setter
    private int points;
    private LocalDateTime createdAt;

    public LoyaltyPoints(int points) {
        this(points, LocalDateTime.now());
    }

    public LoyaltyPoints(int points, LocalDateTime createdAt) {
        this.points = points;
        this.createdAt = createdAt;
    }
}
