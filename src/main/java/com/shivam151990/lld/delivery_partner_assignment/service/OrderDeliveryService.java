package com.shivam151990.lld.delivery_partner_assignment.service;

import com.shivam151990.lld.delivery_partner_assignment.exception.InvalidOrderException;
import com.shivam151990.lld.delivery_partner_assignment.exception.NoPartnerAvailableException;
import com.shivam151990.lld.delivery_partner_assignment.model.Order;
import com.shivam151990.lld.delivery_partner_assignment.model.Partner;
import com.shivam151990.lld.delivery_partner_assignment.repo.PartnerRepository;
import com.shivam151990.lld.delivery_partner_assignment.strategy.OrderDeliveryStrategy;

import java.util.List;
import java.util.Optional;

public class OrderDeliveryService {

    private final PartnerRepository partnerRepository;
    private final OrderDeliveryStrategy deliveryStrategy;

    public OrderDeliveryService(PartnerRepository partnerRepository, OrderDeliveryStrategy deliveryStrategy) {
        this.partnerRepository = partnerRepository;
        this.deliveryStrategy = deliveryStrategy;
    }

    public void addPartner(Partner partner) {
        partnerRepository.addPartner(partner);
    }

    public Partner assignOrder(Order order) {
        if (order == null) {
            throw new InvalidOrderException("Pass a valid order");
        }

        List<Partner> availablePartners = partnerRepository.getAvailablePartners();
        Partner partner = deliveryStrategy.selectPartner(order, availablePartners)
                .orElseThrow(() -> new NoPartnerAvailableException("No partner available for order " + order.getId()));

        partnerRepository.assign(order.getId(), partner);
        return partner;
    }

    public void completeOrder(String orderId) {
        Optional<Partner> partner = partnerRepository.free(orderId);
        if (partner.isEmpty()) {
            throw new InvalidOrderException("No active assignment found for order " + orderId);
        }
    }
}
