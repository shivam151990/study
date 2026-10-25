package com.shivam151990.lld.basket_restriction_engine.service;

import com.shivam151990.lld.basket_restriction_engine.model.ProductQuantity;
import com.shivam151990.lld.basket_restriction_engine.repo.BasketRepo;

import java.util.Optional;

/**
 * A single basket restriction. New rule types are added by implementing this interface,
 * without modifying BasketProcessor or the existing rules (open/closed principle).
 */
public interface RuleFilter {

    /**
     * Checks whether adding {@code productQuantity} on top of the quantities already in {@code counts}
     * breaks this rule.
     *
     * @param productQuantity the item being added
     * @param counts          quantities already in the basket; the rule reads from it but must not modify it
     * @return the violation message if the rule is broken, empty otherwise
     */
    Optional<String> check(ProductQuantity productQuantity, BasketRepo counts);
}
