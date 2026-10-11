package com.shivam151990.lld.delivery_partner_assignment;

import com.shivam151990.lld.delivery_partner_assignment.exception.NoPartnerAvailableException;
import com.shivam151990.lld.delivery_partner_assignment.model.Order;
import com.shivam151990.lld.delivery_partner_assignment.model.Partner;
import com.shivam151990.lld.delivery_partner_assignment.repo.InMemoryPartnerRepository;
import com.shivam151990.lld.delivery_partner_assignment.repo.PartnerRepository;
import com.shivam151990.lld.delivery_partner_assignment.service.OrderDeliveryService;
import com.shivam151990.lld.delivery_partner_assignment.strategy.NearestPartnerDeliveryStrategy;

public class Runner {
    public static void main(String[] args) {
        PartnerRepository partnerRepository = new InMemoryPartnerRepository();
        OrderDeliveryService service = new OrderDeliveryService(partnerRepository, new NearestPartnerDeliveryStrategy());

        Partner p1 = new Partner("P1", 1);
        Partner p2 = new Partner("P2", 3);
        Partner p3 = new Partner("P3", 0.5);
        p3.setAvailable(false); // already busy, mirroring the README example

        service.addPartner(p1);
        service.addPartner(p2);
        service.addPartner(p3);

        Order order1 = new Order("order-1", 0);
        Partner assigned = service.assignOrder(order1);
        System.out.println("Assigned " + assigned.getId() + " to " + order1.getId());

        service.completeOrder(order1.getId());
        System.out.println("P1 available again: " + p1.isAvailable());

        // Exhaust every partner, then show the "no partner available" error
        service.assignOrder(new Order("order-2", 0));
        service.assignOrder(new Order("order-3", 0));
        try {
            service.assignOrder(new Order("order-4", 0));
        } catch (NoPartnerAvailableException e) {
            System.out.println("order-4 failed as expected: " + e.getMessage());
        }
    }
}
