package com.shivam151990.lld.in_memory_database.constraint;

public interface Constraint<T> {
    void validate(String columnName, T value);
}
