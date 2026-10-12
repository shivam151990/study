package com.shivam151990.lld.in_memory_database.constraint;

public class MaxLengthConstraint implements Constraint<String> {

    public static final int MAX_ALLOWED_LENGTH = 20;

    private final int maxLength;

    public MaxLengthConstraint(int maxLength) {
        if (maxLength > MAX_ALLOWED_LENGTH) {
            throw new IllegalArgumentException("Max length cannot exceed " + MAX_ALLOWED_LENGTH);
        }
        this.maxLength = maxLength;
    }

    @Override
    public void validate(String columnName, String value) {
        if (value.length() > maxLength) {
            throw new IllegalArgumentException(
                    "Column " + columnName + " exceeds max length of " + maxLength);
        }
    }
}
