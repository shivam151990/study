package com.shivam151990.lld.notification_service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationService {

    private Map<String, List<NotificationChannel>> preference = new HashMap<>();
    private List<String> failures = new ArrayList<>();

    public void setPreferences(String userId, List<NotificationChannel> channels) {
        preference.put(userId, channels);
    }

    public void send(String userId, String message) {
        List<NotificationChannel> channels = preference.getOrDefault(userId, List.of());
        if (channels.isEmpty()) {
            channels = List.of(NotificationChannel.EMAIL);
        }
        for (NotificationChannel channel : channels) {
            try {
                channel.getChannel().send(message);
            } catch (Exception e) {
                failures.add(channel + " failed for user " + userId + ": " + e.getMessage());
            }
        }
    }

    public List<String> getFailures() {
        return List.copyOf(failures);
    }
}
