package com.shivam151990.lld.in_memory_database.model;

import com.shivam151990.lld.in_memory_database.constraint.Constraint;
import lombok.Getter;

@Getter
public class Column<T> {
    private final String name;
    private final Class<T> type;
    private final boolean mandatory;
    private final Constraint<T> constraint;

    public Column(String name, Class<T> type, boolean mandatory) {
        this(name, type, mandatory, null);
    }

    public Column(String name, Class<T> type, boolean mandatory, Constraint<T> constraint) {
        if (type != String.class && type != Integer.class) {
            throw new IllegalArgumentException("Unsupported column type: " + type.getSimpleName());
        }
        this.name = name;
        this.type = type;
        this.mandatory = mandatory;
        this.constraint = constraint;
    }

    public void validate(Object value) {
        if (value == null) {
            if (mandatory) {
                throw new IllegalArgumentException("Column " + name + " is mandatory");
            }
            return;
        }
        if (!type.isInstance(value)) {
            throw new IllegalArgumentException("Invalid type for column: " + name);
        }
        if (constraint != null) {
            constraint.validate(name, type.cast(value));
        }
    }
}
