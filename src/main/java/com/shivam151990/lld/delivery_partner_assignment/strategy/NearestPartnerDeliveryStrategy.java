package com.shivam151990.lld.delivery_partner_assignment.strategy;

import com.shivam151990.lld.delivery_partner_assignment.model.Order;
import com.shivam151990.lld.delivery_partner_assignment.model.Partner;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class NearestPartnerDeliveryStrategy implements OrderDeliveryStrategy {

    @Override
    public Optional<Partner> selectPartner(Order order, List<Partner> availablePartners) {
        return availablePartners.stream()
                .min(Comparator.comparingDouble(partner -> Math.abs(partner.getLocation() - order.getLocation())));
    }
}
