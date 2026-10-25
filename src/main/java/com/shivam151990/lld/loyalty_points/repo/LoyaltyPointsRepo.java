package com.shivam151990.lld.loyalty_points.repo;

import com.shivam151990.lld.loyalty_points.model.UserHistory;
import com.shivam151990.lld.loyalty_points.model.LoyaltyPoints;

import java.time.LocalDateTime;
import java.util.List;

public interface LoyaltyPointsRepo {
    void addPoints(String user, LoyaltyPoints points);
    int getLoyaltyPoints(String user);
    void redeem(String userId, int points);
    int getUnExpiredPointCount(String userId, LocalDateTime now);
    List<UserHistory> getHistory(String userId);
}
