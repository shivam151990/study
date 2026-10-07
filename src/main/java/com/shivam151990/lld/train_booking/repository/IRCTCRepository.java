package com.shivam151990.lld.train_booking.repository;

import com.shivam151990.lld.train_booking.model.Booking;
import com.shivam151990.lld.train_booking.model.Seat;
import com.shivam151990.lld.train_booking.model.Train;
import com.shivam151990.lld.train_booking.model.TrainRoute;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IRCTCRepository {

    List<Train> createTrain(int count);

    Map<Train, List<Seat>> createSeatsPerTrain(List<Train> trains, int seatCount);

    void addTrainRoute(Train t, TrainRoute tr);

    List<Seat> getSeats(Train train);

    Optional<TrainRoute> getRoute(Train train);

    List<TrainRoute> getAllRoutes();

    void saveBooking(Booking booking);

    Optional<Booking> findBooking(String bookingId);
}
