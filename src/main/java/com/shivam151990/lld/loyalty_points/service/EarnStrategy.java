package com.shivam151990.lld.loyalty_points.service;

import com.shivam151990.lld.loyalty_points.model.PurchaseCategory;

public interface EarnStrategy {
    int getLoyaltyPoints(PurchaseCategory category, int purchaseAmount);
}
