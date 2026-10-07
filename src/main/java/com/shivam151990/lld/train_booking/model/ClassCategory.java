package com.shivam151990.lld.train_booking.model;

import java.util.concurrent.ThreadLocalRandom;

public enum ClassCategory {
    AC1, AC2, AC3, SLEEPER;

    private static final ClassCategory[] VALUES = values();

    public static ClassCategory random() {
        return VALUES[ThreadLocalRandom.current().nextInt(VALUES.length)];
    }
}
