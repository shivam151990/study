package com.shivam151990.lld.delivery_partner_assignment.strategy;

import com.shivam151990.lld.delivery_partner_assignment.model.Order;
import com.shivam151990.lld.delivery_partner_assignment.model.Partner;

import java.util.List;
import java.util.Optional;

public interface OrderDeliveryStrategy {
    Optional<Partner> selectPartner(Order order, List<Partner> availablePartners);
}
