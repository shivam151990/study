package com.shivam151990.lld.coupon.model;

import lombok.Getter;

@Getter
public class Product {

    private String categoryId;
    private String name;
    private double price;

    public Product(String categoryId, String name, double price) {
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
    }
}
