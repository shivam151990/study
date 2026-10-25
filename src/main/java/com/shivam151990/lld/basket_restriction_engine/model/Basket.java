package com.shivam151990.lld.basket_restriction_engine.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Basket {

    private List<ProductQuantity> products;

    public Basket(List<ProductQuantity> products) {
        this.products = products;
    }
}
