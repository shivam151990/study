package com.shivam151990.lld.loyalty_points.service;

import com.shivam151990.lld.loyalty_points.model.PurchaseCategory;
import com.shivam151990.lld.loyalty_points.repo.LoyaltyPointsRepo;

import java.util.Map;

public class PointsForCategory implements EarnStrategy {

    private Map<PurchaseCategory, Integer> overrides;
    private LoyaltyPointsRepo pointsRepo;

    public PointsForCategory(Map<PurchaseCategory, Integer> overrides, LoyaltyPointsRepo pointsRepo) {
        this.overrides = overrides;
        this.pointsRepo = pointsRepo;
    }

    @Override
    public int getLoyaltyPoints(PurchaseCategory category, int purchaseAmount) {
        int multiplier = overrides.getOrDefault(category, 1);
        return (purchaseAmount / 100) * multiplier;
    }
}
