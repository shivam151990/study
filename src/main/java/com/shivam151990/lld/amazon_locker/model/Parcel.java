package com.shivam151990.lld.amazon_locker.model;

public enum Parcel {
    SMALL(0), MEDIUM(1), LARGE(2);

    private final int value;
    Parcel(int i) {
        value = i;
    }

    public int getValue() {
        return value;
    }
}
