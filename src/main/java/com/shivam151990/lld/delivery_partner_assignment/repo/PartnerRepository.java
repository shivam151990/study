package com.shivam151990.lld.delivery_partner_assignment.repo;

import com.shivam151990.lld.delivery_partner_assignment.model.Partner;

import java.util.List;
import java.util.Optional;

public interface PartnerRepository {

    void addPartner(Partner partner);

    List<Partner> getAvailablePartners();

    void assign(String orderId, Partner partner);

    Optional<Partner> free(String orderId);
}
