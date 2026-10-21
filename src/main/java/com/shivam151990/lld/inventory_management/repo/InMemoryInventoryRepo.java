package com.shivam151990.lld.inventory_management.repo;

import com.shivam151990.lld.inventory_management.exception.InvalidItemQuantityException;
import com.shivam151990.lld.inventory_management.model.InventoryItem;
import com.shivam151990.lld.inventory_management.model.Invoice;
import com.shivam151990.lld.inventory_management.model.Product;
import com.shivam151990.lld.inventory_management.model.ShoppingCart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryInventoryRepo implements InventoryRepo {

    private final Map<String, InventoryItem> inventory = new ConcurrentHashMap<>();

    @Override
    public void addProduct(String productId) {
        // compute is atomic per key, so two threads adding the same new product can't both create it
        inventory.compute(productId, (id, item) -> {
            if (item == null) {
                return new InventoryItem(new Product(id), 1);
            }
            item.increment();
            return item;
        });
    }

    // Lock-free snapshot; the answer can be stale by the time the caller acts on it.
    // bookItems re-validates under locks, so treat this only as a hint.
    @Override
    public boolean checkAvailability(ShoppingCart items) {
        for (Map.Entry<String, Integer> entry: items.getCart().entrySet()) {
            InventoryItem item = inventory.get(entry.getKey());
            if (item == null || item.getQuantity() - entry.getValue() < 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Invoice bookItems(ShoppingCart products) {
        Map<String, Integer> order = new TreeMap<>(products.getCart());
        List<InventoryItem> locked = new ArrayList<>();
        try {
            for (String productId : order.keySet()) {
                InventoryItem item = inventory.get(productId);
                if (item == null) {
                    throw new InvalidItemQuantityException("Product " + productId + " not found");
                }
                item.lock();
                locked.add(item);
            }
            // validate everything before mutating anything, so a failure never leaves a partial booking
            for (InventoryItem item : locked) {
                if (item.getQuantity() - order.get(item.getProduct().id()) < 0) {
                    throw new InvalidItemQuantityException(
                            "Quantity of item " + item.getProduct().id() + " not available");
                }
            }
            for (InventoryItem item : locked) {
                String productId = item.getProduct().id();
                item.decrement(order.get(productId));
                System.out.println("Item " + productId + " booked ");
            }
        } finally {
            for (InventoryItem item : locked) {
                item.unlock();
            }
        }
        return new Invoice(UUID.randomUUID().toString().substring(0, 6));
    }
}
