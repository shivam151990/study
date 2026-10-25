package com.shivam151990.lld.stock_observer.model;

import lombok.Getter;
import lombok.Setter;


public class Threshold {
    @Getter
    private int val;

    @Setter
    private boolean breachBelow;

    @Setter
    private boolean breachAbove;

    public Threshold(int val) {
        this.val = val;
    }

    public boolean hasBreachedBelow() {
        return breachBelow;
    }

    public boolean hasBreachedAbove() {
        return breachAbove;
    }
}
