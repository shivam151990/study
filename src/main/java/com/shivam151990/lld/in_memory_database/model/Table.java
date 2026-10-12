package com.shivam151990.lld.in_memory_database.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Table {

    private final String name;
    private final Map<String, Column<?>> columns;
    private final List<Row> rows;

    public Table(String name) {
        this.name = name;
        columns = new LinkedHashMap<>();
        rows = new ArrayList<>();
    }

    public void addColumn(Column<?> column) {
        if (columns.containsKey(column.getName())) {
            throw new IllegalArgumentException("Column already exists: " + column.getName());
        }
        if (column.isMandatory() && !rows.isEmpty()) {
            throw new IllegalArgumentException(
                    "Cannot add mandatory column to a non-empty table: " + column.getName());
        }
        columns.put(column.getName(), column);
    }

    public void dropColumn(String columnName) {
        if (columns.remove(columnName) == null) {
            throw new IllegalArgumentException("Unknown column: " + columnName);
        }
        rows.forEach(row -> row.remove(columnName));
    }

    public void insert(Row row) {
        if (row == null) {
            throw new IllegalArgumentException("Row cannot be null");
        }

        for (Column<?> column : columns.values()) {
            // get() also checks the stored value's type using Class.cast().
            Object value = row.get(column);

            if (column.isMandatory() && value == null) {
                throw new IllegalArgumentException(
                        "Mandatory column missing or null: " + column.getName());
            }
            if (value != null) {
                column.validate(value);
            }
        }

        rows.add(row);
    }

    public List<Row> getAll() {
        return List.copyOf(rows);
    }

    public List<Row> filter(String columnName, Object value) {
        if (!columns.containsKey(columnName)) {
            throw new IllegalArgumentException("Unknown column: " + columnName);
        }
        return rows.stream()
                .filter(row -> Objects.equals(row.get(columnName), value))
                .collect(Collectors.toList());
    }

    public void printAll() {
        print(rows);
    }

    public void printFiltered(String columnName, Object value) {
        print(filter(columnName, value));
    }

    private void print(List<Row> toPrint) {
        System.out.println("Table: " + name);
        System.out.println(String.join(" | ", columns.keySet()));
        for (Row row : toPrint) {
            System.out.println(columns.keySet().stream()
                    .map(col -> String.valueOf(row.get(col)))
                    .collect(Collectors.joining(" | ")));
        }
    }
}
