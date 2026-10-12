package com.shivam151990.lld.in_memory_database;

import com.shivam151990.lld.in_memory_database.constraint.MaxLengthConstraint;
import com.shivam151990.lld.in_memory_database.constraint.MinValueConstraint;
import com.shivam151990.lld.in_memory_database.model.Column;
import com.shivam151990.lld.in_memory_database.model.Database;
import com.shivam151990.lld.in_memory_database.model.Row;
import com.shivam151990.lld.in_memory_database.model.Table;

public class InMemoryDatabaseRunner {

    public static void main(String[] args) {
        Database db = new Database();
        Table users = db.createTable("users");
        Column<String> name = new Column<>("name", String.class, true, new MaxLengthConstraint(20));
        Column<Integer> id = new Column<>("id", Integer.class, true, new MinValueConstraint(1024));
        Column<String> city = new Column<>("city", String.class, false);
        users.addColumn(name);
        users.addColumn(id);
        users.addColumn(city);

        users.insert(row(name, "Alice", id, 1024, city, "Pune"));
        users.insert(row(name, "Bob", id, 2048, city, null));
        users.insert(row(name, "Carol", id, 4096, city, "Pune"));

        users.printAll();
        System.out.println("-- city = Pune");
        users.printFiltered("city", "Pune");

        tryInsert(users, row(name, "A name that is far too long", id, 5000, city, null));
        tryInsert(users, row(name, "Dave", id, 10, city, null));
        tryInsert(users, row(name, null, id, 3000, city, null));

        users.dropColumn("city");
        users.printAll();
        db.deleteTable("users");
    }

    private static Row row(Column<String> name, String nameVal, Column<Integer> id, Integer idVal,
                           Column<String> city, String cityVal) {
        Row row = new Row();
        row.setValue(name, nameVal);
        row.setValue(id, idVal);
        row.setValue(city, cityVal);
        return row;
    }

    private static void tryInsert(Table table, Row row) {
        try {
            table.insert(row);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
    }
}
