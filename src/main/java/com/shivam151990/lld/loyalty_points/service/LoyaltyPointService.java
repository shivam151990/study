package com.shivam151990.lld.loyalty_points.service;

import com.shivam151990.lld.loyalty_points.repo.LoyaltyPointsRepo;
import com.shivam151990.lld.loyalty_points.model.PurchaseCategory;
import com.shivam151990.lld.loyalty_points.model.UserHistory;
import com.shivam151990.lld.loyalty_points.model.LoyaltyPoints;

import java.time.LocalDateTime;
import java.util.List;

public class LoyaltyPointService {

    private EarnStrategy earnStrategy;
    private LoyaltyPointsRepo pointsRepo;

    public LoyaltyPointService(EarnStrategy earnStrategy, LoyaltyPointsRepo pointsRepo) {
        this.earnStrategy = earnStrategy;
        this.pointsRepo = pointsRepo;
    }

    public void earn(String userId, int amount, PurchaseCategory category) {
        int loyaltyPoints = earnStrategy.getLoyaltyPoints(category, amount);
        LoyaltyPoints points = new LoyaltyPoints(loyaltyPoints);
        pointsRepo.addPoints(userId, points);
    }

    public void redeem(String userId, int points) {
        pointsRepo.redeem(userId, points);
        System.out.println("Loyalty points redeemed for user: " + userId);
    }

    public int getBalance(String userId, LocalDateTime now) {
        return pointsRepo.getUnExpiredPointCount(userId, now);
    }

    public List<UserHistory> getHistory(String userId) {
        return pointsRepo.getHistory(userId);
    }


}
