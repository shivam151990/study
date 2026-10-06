package com.shivam151990.lld.gdpr.service;

import com.shivam151990.lld.gdpr.model.User;
import com.shivam151990.lld.gdpr.model.UserActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDeletionService implements DeletionService {

    private final List<User> users;
    private final List<UserActivity> activities;
    private final List<DeletionRule> rules;

    public UserDeletionService(List<User> users, List<UserActivity> activities, List<DeletionRule> rules) {
        this.users = users;
        this.activities = activities;
        this.rules = rules;
    }

    @Override
    public List<String> delete() {
        Map<String, List<UserActivity>> activitiesByUser = activities.stream()
                .collect(Collectors.groupingBy(UserActivity::getUserId));

        List<String> usersToDelete = new ArrayList<>();
        for (User user : users) {
            List<UserActivity> userActivities = activitiesByUser.getOrDefault(user.getUserId(), List.of());
            boolean canDelete = rules.stream().allMatch(rule -> rule.canDelete(userActivities));
            if (canDelete) {
                usersToDelete.add(user.getUserId());
            }
        }
        return usersToDelete;
    }
}
