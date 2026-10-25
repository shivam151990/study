package com.shivam151990.lld.stock_observer.model;

import com.shivam151990.lld.stock_observer.service.StockObserver;
import lombok.Getter;

@Getter
public class User implements StockObserver {

    private String id;

    public User(String id) {
        this.id = id;
    }

    @Override
    public void receive(String msg) {
        System.out.println("User " + id + " : " + msg);
    }
}
