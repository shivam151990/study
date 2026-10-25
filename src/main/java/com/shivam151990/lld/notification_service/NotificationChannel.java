package com.shivam151990.lld.notification_service;

public enum NotificationChannel {

    EMAIL(new EmailChannel()),
    SMS(new SmsChannel()),
    PUSH(new PushChannel());

    private final Channel channel;

    NotificationChannel(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }
}
