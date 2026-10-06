package com.shivam151990.lld.gdpr.model;

import lombok.Getter;

@Getter
public class User {

    private final String userId;

    public User(String userId) {
        this.userId = userId;
    }
}
