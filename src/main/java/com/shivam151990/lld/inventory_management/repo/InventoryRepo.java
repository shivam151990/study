package com.shivam151990.lld.inventory_management.repo;

import com.shivam151990.lld.inventory_management.model.Invoice;
import com.shivam151990.lld.inventory_management.model.ShoppingCart;

public interface InventoryRepo {

    void addProduct(String productId);

    boolean checkAvailability(ShoppingCart items);

    Invoice bookItems(ShoppingCart products);
}
