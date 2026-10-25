package com.shivam151990.lld.loyalty_points.repo;

import com.shivam151990.lld.loyalty_points.model.LoyaltyPoints;
import com.shivam151990.lld.loyalty_points.exception.LoyaltyPointsRedemptionException;
import com.shivam151990.lld.loyalty_points.model.UserHistory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class InMemLoyaltyPointsRepo implements LoyaltyPointsRepo {

    private final Map<String, Integer> loyaltyPoints;
    private final Map<String, LinkedList<LoyaltyPoints>> loyaltyPointsTs;
    private final Map<String, List<UserHistory>> history;

    public InMemLoyaltyPointsRepo() {
        loyaltyPoints = new HashMap<>();
        loyaltyPointsTs = new HashMap<>();
        history = new HashMap<>();
    }

    @Override
    public void addPoints(String user, LoyaltyPoints points) {
        loyaltyPoints.put(user, loyaltyPoints.getOrDefault(user, 0) + points.getPoints());
        loyaltyPointsTs.computeIfAbsent(user, l -> new LinkedList<>());
        history.computeIfAbsent(user, l -> new ArrayList<>());
        loyaltyPointsTs.get(user).add(points);
        history.get(user).add(new UserHistory(UserHistory.Op.EARNED, user, points.getPoints()));

    }

    @Override
    public int getLoyaltyPoints(String user) {
        return loyaltyPoints.getOrDefault(user, 0);
    }

    @Override
    public void redeem(String userId, int points) {
        removeExpired(userId, LocalDateTime.now());
        validateLoyaltyPoints(userId, points);
        loyaltyPoints.put(userId, loyaltyPoints.get(userId) - points);
        // Use up the oldest points first, so expiry later only removes points that are left
        LinkedList<LoyaltyPoints> userLoyaltyPoints = loyaltyPointsTs.get(userId);
        int remaining = points;
        while (remaining > 0) {
            LoyaltyPoints oldest = userLoyaltyPoints.peek();
            if (oldest.getPoints() <= remaining) {
                remaining -= Objects.requireNonNull(userLoyaltyPoints.poll()).getPoints();
            } else {
                oldest.setPoints(oldest.getPoints() - remaining);
                remaining = 0;
            }
        }
        history.get(userId).add(new UserHistory(UserHistory.Op.REDEEMED, userId, points));
    }

    @Override
    public int getUnExpiredPointCount(String userId, LocalDateTime now) {
        removeExpired(userId, now);
        if (!loyaltyPoints.containsKey(userId)) {
            throw new LoyaltyPointsRedemptionException("Cannot redeem loyalty points, user does not exits");
        }
        return loyaltyPoints.get(userId);
    }

    @Override
    public List<UserHistory> getHistory(String userId) {
        if (!history.containsKey(userId)) {
            return List.of();
        }
        return history.get(userId);
    }

    private void removeExpired(String userId, LocalDateTime now) {
        if (!loyaltyPointsTs.containsKey(userId)) {
            return;
        }
        LinkedList<LoyaltyPoints> userLoyaltyPoints = loyaltyPointsTs.get(userId);
        int expired = 0;
        while (!userLoyaltyPoints.isEmpty()) {
            LocalDateTime pointsCreateTs = userLoyaltyPoints.peek().getCreatedAt();
            if (ChronoUnit.MONTHS.between(pointsCreateTs, now) >= 12) {
                expired += Objects.requireNonNull(userLoyaltyPoints.poll()).getPoints();
            } else {
                break;
            }
        }
        loyaltyPoints.put(userId, loyaltyPoints.get(userId) - expired);
    }

    private void validateLoyaltyPoints(String userId, int points) {
        if (!loyaltyPoints.containsKey(userId)) {
            throw new LoyaltyPointsRedemptionException("Cannot redeem loyalty points, user does not exits");
        }
        if (points > loyaltyPoints.get(userId)) {
            throw new LoyaltyPointsRedemptionException("Cannot redeem, not enough loyalty points");
        }
    }
}
