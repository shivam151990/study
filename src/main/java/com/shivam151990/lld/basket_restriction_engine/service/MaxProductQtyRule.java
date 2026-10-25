package com.shivam151990.lld.basket_restriction_engine.service;

import com.shivam151990.lld.basket_restriction_engine.model.ProductQuantity;
import com.shivam151990.lld.basket_restriction_engine.repo.BasketRepo;

import java.util.Map;
import java.util.Optional;

/** Limits how many units of a single product the basket can hold. Products without a limit are unrestricted. */
public class MaxProductQtyRule implements RuleFilter {

    // productId -> max quantity allowed in the basket
    private final Map<String, Integer> maxQtyPerProduct;

    public MaxProductQtyRule(Map<String, Integer> maxQtyPerProduct) {
        this.maxQtyPerProduct = maxQtyPerProduct;
    }

    @Override
    public Optional<String> check(ProductQuantity productQuantity, BasketRepo counts) {
        String id = productQuantity.id();
        Integer max = maxQtyPerProduct.get(id);
        if (max == null) {
            return Optional.empty();
        }
        // Quantity already in the basket plus the quantity being added
        int newQty = counts.getProductCount(id) + productQuantity.qty();
        if (newQty > max) {
            return Optional.of("Max quantity " + max + " exceeded for product " + id);
        }
        return Optional.empty();
    }
}
