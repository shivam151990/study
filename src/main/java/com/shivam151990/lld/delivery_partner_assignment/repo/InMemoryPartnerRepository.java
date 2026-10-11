package com.shivam151990.lld.delivery_partner_assignment.repo;

import com.shivam151990.lld.delivery_partner_assignment.model.Partner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryPartnerRepository implements PartnerRepository {

    private final Map<String, Partner> partnersById = new HashMap<>();
    private final Map<String, Partner> assignedPartnerByOrderId = new HashMap<>();

    @Override
    public void addPartner(Partner partner) {
        partnersById.put(partner.getId(), partner);
    }

    @Override
    public List<Partner> getAvailablePartners() {
        return partnersById.values().stream()
                .filter(Partner::isAvailable)
                .collect(Collectors.toList());
    }

    @Override
    public void assign(String orderId, Partner partner) {
        partner.setAvailable(false);
        assignedPartnerByOrderId.put(orderId, partner);
    }

    @Override
    public Optional<Partner> free(String orderId) {
        Partner partner = assignedPartnerByOrderId.remove(orderId);
        if (partner != null) {
            partner.setAvailable(true);
        }
        return Optional.ofNullable(partner);
    }
}
