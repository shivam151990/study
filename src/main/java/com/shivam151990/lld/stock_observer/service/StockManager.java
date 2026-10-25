package com.shivam151990.lld.stock_observer.service;

import com.shivam151990.lld.stock_observer.exception.StockNotAvailabeException;
import com.shivam151990.lld.stock_observer.model.Threshold;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockManager implements StockObservable {

    private Map<String, Integer> productStock;
    private Map<String, Threshold> threshold;
    private List<StockObserver> observers;

    public StockManager() {
        productStock = new HashMap<>();
        threshold = new HashMap<>();
        observers = new ArrayList<>();
    }

    public void setThreshold(String id, int qty) {
        threshold.put(id, new Threshold(qty));
    }

    public void addStock(String id, int qty) {
        productStock.put(id, productStock.getOrDefault(id, 0) + qty);
        int newStock = productStock.get(id);
        if (threshold.containsKey(id)) {
            Threshold t = threshold.get(id);
            if (newStock > t.getVal() && !t.hasBreachedAbove()) {
                t.setBreachAbove(true);
                t.setBreachBelow(false);
            }
        }
    }

    public void removeStock(String id, int qty) {
        if (!productStock.containsKey(id)) {
            throw new StockNotAvailabeException("No quantity available");
        }
        if (productStock.get(id) < qty) {
            throw new StockNotAvailabeException("Insufficient quantity");
        }
        int newStock = productStock.get(id) - qty;
        productStock.put(id, newStock);
        if (threshold.containsKey(id)) {
            Threshold t = threshold.get(id);
            if (newStock <= t.getVal() && !t.hasBreachedBelow()) {
                t.setBreachBelow(true);
                t.setBreachAbove(false);
                notifyChange("Value breached below " + t.getVal() + " for product " + id);
            }
        }
    }

    public int getStock(String id) {
        return productStock.getOrDefault(id, 0);
    }

    @Override
    public void add(StockObserver obs) {
        observers.add(obs);
    }

    @Override
    public void remove(StockObserver obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyChange(String change) {
        for (StockObserver observer: observers) {
            observer.receive(change);
        }
    }
}
