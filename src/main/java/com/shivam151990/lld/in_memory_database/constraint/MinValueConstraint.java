package com.shivam151990.lld.in_memory_database.constraint;

public class MinValueConstraint implements Constraint<Integer> {

    public static final int MIN_ALLOWED_VALUE = 1024;

    private final int minValue;

    public MinValueConstraint(int minValue) {
        if (minValue < MIN_ALLOWED_VALUE) {
            throw new IllegalArgumentException("Min value cannot be less than " + MIN_ALLOWED_VALUE);
        }
        this.minValue = minValue;
    }

    @Override
    public void validate(String columnName, Integer value) {
        if (value < minValue) {
            throw new IllegalArgumentException(
                    "Column " + columnName + " must be at least " + minValue);
        }
    }
}
