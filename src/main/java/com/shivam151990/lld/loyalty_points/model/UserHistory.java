package com.shivam151990.lld.loyalty_points.model;

import lombok.Getter;

@Getter
public class UserHistory {

    public enum Op {
        EARNED, REDEEMED
    }

    private Op operation;
    private String userId;
    private int points;

    public UserHistory(Op operation, String userId, int points) {
        this.operation = operation;
        this.userId = userId;
        this.points = points;
    }

    @Override
    public String toString() {
        return points + " " + operation + " by " + userId;
    }
}
