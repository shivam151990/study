package com.shivam151990.lld.gdpr;

import com.shivam151990.lld.gdpr.model.ActivityType;
import com.shivam151990.lld.gdpr.model.User;
import com.shivam151990.lld.gdpr.model.UserActivity;
import com.shivam151990.lld.gdpr.service.DeletionRule;
import com.shivam151990.lld.gdpr.service.DeletionService;
import com.shivam151990.lld.gdpr.service.UserDeletionService;
import com.shivam151990.lld.gdpr.service.ValidDayCheckRule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();

        List<User> users = List.of(
                new User("user1"),
                new User("user2"),
                new User("user3")
        );

        List<UserActivity> activities = List.of(
                // user1: old login, but also a recent login -> should NOT be deleted
                new UserActivity("user1", ActivityType.WEB_LOGIN, now.minusDays(120)),
                new UserActivity("user1", ActivityType.APP_LOGIN, now.minusDays(5)),

                // user2: only a marketing email interaction, which is ignored -> should be deleted
                new UserActivity("user2", ActivityType.MARKETING, now.minusDays(1))

                // user3: no activity at all -> should be deleted
        );

        DeletionRule inactivityRule = new ValidDayCheckRule(90, Set.of(ActivityType.MARKETING));
        DeletionService deletionService = new UserDeletionService(users, activities, List.of(inactivityRule));

        List<String> usersReadyForDeletion = deletionService.delete();
        System.out.println("Users ready for deletion: " + usersReadyForDeletion);
    }
}
