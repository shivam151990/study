package com.shivam151990.lld.train_booking.service;

import com.shivam151990.lld.train_booking.exception.BookingNotFoundException;
import com.shivam151990.lld.train_booking.exception.InvalidRouteException;
import com.shivam151990.lld.train_booking.exception.SeatUnavailableException;
import com.shivam151990.lld.train_booking.model.Booking;
import com.shivam151990.lld.train_booking.model.BookingRequest;
import com.shivam151990.lld.train_booking.model.BookingStatus;
import com.shivam151990.lld.train_booking.model.Seat;
import com.shivam151990.lld.train_booking.model.TrainRoute;
import com.shivam151990.lld.train_booking.repository.IRCTCRepository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TrainBookingService implements BookingService {

    private final IRCTCRepository repository;

    // One lock per train keeps "find N available seats, then reserve them" atomic,
    // so two concurrent bookings on the same train can never claim the same seat
    // and a booking can never partially succeed.
    private final ConcurrentHashMap<Integer, Object> trainLocks = new ConcurrentHashMap<>();

    public TrainBookingService(IRCTCRepository repository) {
        this.repository = repository;
    }

    @Override
    public Booking book(BookingRequest request) {
        TrainRoute route = repository.getRoute(request.getTrain())
                .orElseThrow(() -> new InvalidRouteException(
                        "No route found for train " + request.getTrain().getTrainNumber()));

        if (!route.matches(request.getSource(), request.getDestination())) {
            throw new InvalidRouteException("Train " + request.getTrain().getTrainNumber()
                    + " does not travel from " + request.getSource() + " to " + request.getDestination());
        }

        Object lock = trainLocks.computeIfAbsent(request.getTrain().getTrainNumber(), id -> new Object());
        synchronized (lock) {
            List<Seat> seatsToBook = repository.getSeats(request.getTrain()).stream()
                    .filter(seat -> seat.getCategory() == request.getCategory())
                    .filter(Seat::isAvailable)
                    .limit(request.getSeatCount())
                    .collect(Collectors.toList());

            if (seatsToBook.size() < request.getSeatCount()) {
                throw new SeatUnavailableException("Only " + seatsToBook.size()
                        + " seat(s) available in " + request.getCategory()
                        + " for " + request.getSource() + " -> " + request.getDestination());
            }

            String bookingId = UUID.randomUUID().toString();
            seatsToBook.forEach(seat -> seat.reserve(bookingId));

            Booking booking = Booking.builder(bookingId, request.getTrain().getTrainNumber(), request.getUserId())
                    .route(request.getSource(), request.getDestination())
                    .category(request.getCategory())
                    .seats(seatsToBook)
                    .build();

            repository.saveBooking(booking);
            return booking;
        }
    }

    @Override
    public void cancel(String bookingId) {
        Booking booking = repository.findBooking(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("No booking found with id " + bookingId));

        Object lock = trainLocks.computeIfAbsent(booking.getTrainId(), id -> new Object());
        synchronized (lock) {
            if (booking.getStatus() == BookingStatus.CANCELLED) {
                return;
            }
            booking.getSeats().forEach(seat -> seat.release(bookingId));
            booking.markCancelled();
        }
    }
}
