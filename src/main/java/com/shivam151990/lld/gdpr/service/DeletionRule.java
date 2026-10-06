package com.shivam151990.lld.gdpr.service;

import com.shivam151990.lld.gdpr.model.UserActivity;

import java.util.List;

public interface DeletionRule {
    boolean canDelete(List<UserActivity> userActivities);
}
