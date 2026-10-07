package com.shivam151990.lld.train_booking.repository;

import com.shivam151990.lld.train_booking.model.Booking;
import com.shivam151990.lld.train_booking.model.ClassCategory;
import com.shivam151990.lld.train_booking.model.Seat;
import com.shivam151990.lld.train_booking.model.Train;
import com.shivam151990.lld.train_booking.model.TrainRoute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemIRCTCRepository implements IRCTCRepository {

    private final Map<Integer, List<Seat>> seatsByTrainId = new ConcurrentHashMap<>();
    private final Map<Integer, TrainRoute> routeByTrainId = new ConcurrentHashMap<>();
    private final Map<String, Booking> bookingsById = new ConcurrentHashMap<>();

    @Override
    public List<Train> createTrain(int count) {
        List<Train> train = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            train.add(new Train(i));
        }
        return train;
    }

    @Override
    public Map<Train, List<Seat>> createSeatsPerTrain(List<Train> trains, int seatCount) {
        Map<Train, List<Seat>> trainSeats = new HashMap<>();
        for (Train t : trains) {
            List<Seat> seats = new ArrayList<>();
            for (int i = 1; i <= seatCount; i++) {
                seats.add(new Seat(ClassCategory.random(), i, t.getTrainNumber()));
            }
            trainSeats.put(t, seats);
            seatsByTrainId.put(t.getTrainNumber(), seats);
        }
        return trainSeats;
    }

    @Override
    public void addTrainRoute(Train t, TrainRoute tr) {
        routeByTrainId.put(t.getTrainNumber(), tr);
    }

    @Override
    public List<Seat> getSeats(Train train) {
        return seatsByTrainId.getOrDefault(train.getTrainNumber(), List.of());
    }

    @Override
    public Optional<TrainRoute> getRoute(Train train) {
        return Optional.ofNullable(routeByTrainId.get(train.getTrainNumber()));
    }

    @Override
    public List<TrainRoute> getAllRoutes() {
        return new ArrayList<>(routeByTrainId.values());
    }

    @Override
    public void saveBooking(Booking booking) {
        bookingsById.put(booking.getBookingId(), booking);
    }

    @Override
    public Optional<Booking> findBooking(String bookingId) {
        return Optional.ofNullable(bookingsById.get(bookingId));
    }
}
