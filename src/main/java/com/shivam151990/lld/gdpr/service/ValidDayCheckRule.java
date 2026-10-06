package com.shivam151990.lld.gdpr.service;

import com.shivam151990.lld.gdpr.model.ActivityType;
import com.shivam151990.lld.gdpr.model.UserActivity;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

public class ValidDayCheckRule implements DeletionRule {

    private final int maxInactiveDays;
    private final Set<ActivityType> ignoredActivityTypes;
    private final Clock clock;

    public ValidDayCheckRule(int maxInactiveDays, Set<ActivityType> ignoredActivityTypes) {
        this(maxInactiveDays, ignoredActivityTypes, Clock.systemDefaultZone());
    }

    public ValidDayCheckRule(int maxInactiveDays, Set<ActivityType> ignoredActivityTypes, Clock clock) {
        this.maxInactiveDays = maxInactiveDays;
        this.ignoredActivityTypes = ignoredActivityTypes;
        this.clock = clock;
    }

    @Override
    public boolean canDelete(List<UserActivity> userActivities) {
        LocalDateTime now = LocalDateTime.now(clock);
        return userActivities.stream()
                .filter(activity -> !ignoredActivityTypes.contains(activity.getActivity()))
                .allMatch(activity -> ChronoUnit.DAYS.between(activity.getActivityTime(), now) > maxInactiveDays);
    }
}
