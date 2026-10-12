package com.shivam151990.lld.in_memory_database.model;

import java.util.HashMap;
import java.util.Map;

public class Database {

    private Map<String, Table> tableMp;

    public Database() {
        tableMp = new HashMap<>();
    }

    public Table createTable(String name) {
        if (tableMp.containsKey(name)) {
            throw new RuntimeException("Table already exists");
        }
        Table table = new Table(name);
        tableMp.put(name, table);
        return table;
    }

    public Table getTable(String name) {
        Table table = tableMp.get(name);
        if (table == null) {
            throw new RuntimeException("Table does not exist");
        }
        return table;
    }

    public void deleteTable(String name) {
        if (tableMp.remove(name) == null) {
            throw new RuntimeException("Table does not exist");
        }
    }

}
