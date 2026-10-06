package com.shivam151990.lld.gdpr.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserActivity {

    private String userId;
    private ActivityType activity;
    private LocalDateTime activityTime;


    public UserActivity(String userId, ActivityType activity, LocalDateTime activityTime) {
        this.userId = userId;
        this.activity = activity;
        this.activityTime = activityTime;
    }
}
