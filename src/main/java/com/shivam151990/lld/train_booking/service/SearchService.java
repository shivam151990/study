package com.shivam151990.lld.train_booking.service;

import com.shivam151990.lld.train_booking.model.SearchCriteria;
import com.shivam151990.lld.train_booking.model.TrainSeatAvailabilityResponse;

import java.util.List;

public interface SearchService {
    List<TrainSeatAvailabilityResponse> search(SearchCriteria criteria);
}
