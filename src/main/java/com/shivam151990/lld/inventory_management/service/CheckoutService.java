package com.shivam151990.lld.inventory_management.service;

import com.shivam151990.lld.inventory_management.exception.InvalidItemQuantityException;
import com.shivam151990.lld.inventory_management.model.Invoice;
import com.shivam151990.lld.inventory_management.model.ShoppingCart;
import com.shivam151990.lld.inventory_management.repo.InventoryRepo;

import java.util.Optional;

public class CheckoutService {

    private final InventoryRepo inventoryRepo;

    public CheckoutService(InventoryRepo inventoryRepo) {
        this.inventoryRepo = inventoryRepo;
    }

    // No checkAvailability() before bookItems(): that check-then-act lets another thread
    // take the stock in between. bookItems validates and books atomically.
    Optional<Invoice> checkoutCart(ShoppingCart cart) {
        try {
            return Optional.of(inventoryRepo.bookItems(cart));
        } catch (InvalidItemQuantityException e) {
            return Optional.empty();
        }
    }
}
