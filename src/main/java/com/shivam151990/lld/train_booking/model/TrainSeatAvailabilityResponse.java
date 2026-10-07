package com.shivam151990.lld.train_booking.model;

import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class TrainSeatAvailabilityResponse {

    private final int trainId;
    private final ClassCategory category;
    private final int availableSeatCount;

    private TrainSeatAvailabilityResponse(int trainId, ClassCategory category, int availableSeatCount) {
        this.trainId = trainId;
        this.category = category;
        this.availableSeatCount = availableSeatCount;
    }

    public static List<TrainSeatAvailabilityResponse> summarize(int trainId, List<Seat> availableSeats) {
        Map<ClassCategory, Long> countByCategory = availableSeats.stream()
                .collect(Collectors.groupingBy(Seat::getCategory, Collectors.counting()));

        return countByCategory.entrySet().stream()
                .map(entry -> new TrainSeatAvailabilityResponse(trainId, entry.getKey(), entry.getValue().intValue()))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Train " + trainId + " | " + category + " | " + availableSeatCount + " seat(s) available";
    }
}
