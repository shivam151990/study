package com.shivam151990.lld.inventory_management.model;

import com.shivam151990.lld.inventory_management.exception.InvalidItemQuantityException;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

@Getter
public class InventoryItem {

    private final Product product;
    // volatile so lock-free reads (e.g. availability hints) see the latest value
    private volatile int quantity;
    // guards quantity; multi-item operations hold it across several items via lock()/unlock()
    @Getter(AccessLevel.NONE)
    private final ReentrantLock lock = new ReentrantLock();

    public InventoryItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InventoryItem that = (InventoryItem) o;
        return Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(product);
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    public void increment() {
        lock.lock();
        try {
            quantity++;
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {
        decrement(1);
    }

    public void decrement(int val) {
        lock.lock();
        try {
            if (quantity - val < 0) {
                throw new InvalidItemQuantityException("Quantity of items not available");
            }
            quantity -= val;
        } finally {
            lock.unlock();
        }
    }
}
