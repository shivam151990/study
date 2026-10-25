package com.shivam151990.lld.notification_service;

public class EmailChannel implements Channel {

    @Override
    public void send(String msg) {
        System.out.println("EMAIL: " + msg);
    }
}
