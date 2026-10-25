package com.shivam151990.lld.notification_service;

public class SmsChannel implements Channel {

    @Override
    public void send(String msg) {
        System.out.println("SMS: " + msg);
    }
}
