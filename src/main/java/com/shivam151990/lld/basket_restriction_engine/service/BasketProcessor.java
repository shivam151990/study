package com.shivam151990.lld.basket_restriction_engine.service;

import com.shivam151990.lld.basket_restriction_engine.exception.ValidationRuleException;
import com.shivam151990.lld.basket_restriction_engine.model.Basket;
import com.shivam151990.lld.basket_restriction_engine.model.ProductQuantity;
import com.shivam151990.lld.basket_restriction_engine.model.Violation;
import com.shivam151990.lld.basket_restriction_engine.repo.BasketRepo;
import com.shivam151990.lld.basket_restriction_engine.repo.InMemoryBasketRepo;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BasketProcessor {

    private final List<RuleFilter> rules;
    // Current basket that addItem adds to
    private final BasketRepo repo;

    public BasketProcessor(List<RuleFilter> rules, BasketRepo repo) {
        this.rules = rules;
        this.repo = repo;
    }

    /**
     * Adds the item to the current basket, or throws with the first broken rule's message.
     * The item is only saved when every rule passes.
     */
    public void addItem(String product, int qty, String category) {
        ProductQuantity pq = new ProductQuantity(product, qty, category);
        for (RuleFilter rule : rules) {
            Optional<String> violation = rule.check(pq, repo);
            if (violation.isPresent()) {
                throw new ValidationRuleException(violation.get());
            }
        }
        repo.addProduct(product, qty, category);
    }

    /**
     * Returns every rule the given basket breaks. The basket is checked on its own,
     * independent of the items added through addItem.
     */
    public List<Violation> validate(Basket basket) {
        // Running totals for this basket only, so repeated lines of the same product
        // or category add up instead of being checked one line at a time
        BasketRepo runningCounts = new InMemoryBasketRepo();
        // A set, so a limit that stays broken over several lines is reported once
        Set<String> messages = new LinkedHashSet<>();
        for (ProductQuantity pq : basket.getProducts()) {
            for (RuleFilter rule : rules) {
                rule.check(pq, runningCounts).ifPresent(messages::add);
            }
            runningCounts.addProduct(pq.id(), pq.qty(), pq.category());
        }
        return messages.stream().map(Violation::new).toList();
    }
}
