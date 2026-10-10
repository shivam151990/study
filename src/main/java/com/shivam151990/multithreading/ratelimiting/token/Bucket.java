package com.shivam151990.multithreading.ratelimiting.token;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bucket {
    private double tokens;
    private long lastRefillNano;

    public Bucket(double tokens, long lastRefillNano) {
        this.tokens = tokens;
        this.lastRefillNano = lastRefillNano;
    }
}
