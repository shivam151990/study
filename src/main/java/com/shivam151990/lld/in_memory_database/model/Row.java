package com.shivam151990.lld.in_memory_database.model;

import java.util.HashMap;
import java.util.Map;

public class Row {

    private Map<String, Object> rows;

    public Row() {
        rows = new HashMap<>();
    }

    public <T> void setValue(Column<T> col, T val) {
        if (val != null && !col.getType().isInstance(val)) {
            throw new IllegalArgumentException("Invalid type for column: " + col.getName());
        }
        rows.put(col.getName(), val);
    }

    public <T> T get(Column<T> column) {
        return column.getType().cast(rows.get(column.getName()));
    }

    public Object get(String columnName) {
        return rows.get(columnName);
    }

    public void remove(String columnName) {
        rows.remove(columnName);
    }
}
