package com.shivam151990.lld.train_booking.service;

import com.shivam151990.lld.train_booking.model.Seat;
import com.shivam151990.lld.train_booking.model.SearchCriteria;
import com.shivam151990.lld.train_booking.model.TrainRoute;
import com.shivam151990.lld.train_booking.model.TrainSeatAvailabilityResponse;
import com.shivam151990.lld.train_booking.repository.IRCTCRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainSearchService implements SearchService {

    private final IRCTCRepository repository;

    public TrainSearchService(IRCTCRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TrainSeatAvailabilityResponse> search(SearchCriteria criteria) {
        List<TrainSeatAvailabilityResponse> responses = new ArrayList<>();

        for (TrainRoute route : repository.getAllRoutes()) {
            if (!route.getDepartureTime().toLocalDate().equals(criteria.getDate())) {
                continue;
            }
            if (!route.matches(criteria.getSource(), criteria.getDestination())) {
                continue;
            }

            List<Seat> availableSeats = repository.getSeats(route.getTrain()).stream()
                    .filter(Seat::isAvailable)
                    .filter(seat -> criteria.getFilters().stream().allMatch(filter -> filter.test(seat)))
                    .collect(Collectors.toList());

            responses.addAll(TrainSeatAvailabilityResponse.summarize(route.getTrain().getTrainNumber(), availableSeats));
        }

        return responses;
    }
}
