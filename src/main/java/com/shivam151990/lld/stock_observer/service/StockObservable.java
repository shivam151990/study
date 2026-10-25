package com.shivam151990.lld.stock_observer.service;

public interface StockObservable {
    void add(StockObserver obs);
    void remove(StockObserver obs);
    void notifyChange(String change);
}
