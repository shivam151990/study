package com.shivam151990.lld.train_booking;

import com.shivam151990.lld.train_booking.filters.FilterByClassCategory;
import com.shivam151990.lld.train_booking.model.Booking;
import com.shivam151990.lld.train_booking.model.BookingRequest;
import com.shivam151990.lld.train_booking.model.ClassCategory;
import com.shivam151990.lld.train_booking.model.SearchCriteria;
import com.shivam151990.lld.train_booking.model.Seat;
import com.shivam151990.lld.train_booking.model.Train;
import com.shivam151990.lld.train_booking.model.TrainRoute;
import com.shivam151990.lld.train_booking.repository.IRCTCRepository;
import com.shivam151990.lld.train_booking.repository.InMemIRCTCRepository;
import com.shivam151990.lld.train_booking.service.BookingService;
import com.shivam151990.lld.train_booking.service.SearchService;
import com.shivam151990.lld.train_booking.service.TrainBookingService;
import com.shivam151990.lld.train_booking.service.TrainSearchService;

import java.time.LocalDate;
import java.util.List;

public class IRCTC {

    public static void main(String[] args) {
        IRCTCRepository repo = new InMemIRCTCRepository();

        List<Train> trains = repo.createTrain(2);
        repo.createSeatsPerTrain(trains, 20);

        Train train1 = trains.get(0);
        LocalDate travelDate = LocalDate.of(2026, 10, 10);
        repo.addTrainRoute(train1, new TrainRoute(train1, "Mumbai", "Bangalore", travelDate.atTime(9, 0)));

        SearchService searchService = new TrainSearchService(repo);
        BookingService bookingService = new TrainBookingService(repo);

        System.out.println("Availability Mumbai -> Bangalore:");
        SearchCriteria mumbaiToBangalore = SearchCriteria.builder("Mumbai", "Bangalore", travelDate).build();
        searchService.search(mumbaiToBangalore).forEach(System.out::println);

        // Book 3 AC2 seats
        BookingRequest request = BookingRequest.builder(train1, "user-1")
                .journey("Mumbai", "Bangalore")
                .category(ClassCategory.AC2)
                .seatCount(3)
                .build();
        Booking booking = bookingService.book(request);
        System.out.println("\nBooked " + booking.getBookingId()
                + " seats=" + booking.getSeats().stream().map(Seat::getNumber).toList());

        System.out.println("\nAvailability Mumbai -> Bangalore (AC2 only) after booking:");
        SearchCriteria mumbaiToBangaloreAc2 = SearchCriteria.builder("Mumbai", "Bangalore", travelDate)
                .filter(new FilterByClassCategory(ClassCategory.AC2))
                .build();
        searchService.search(mumbaiToBangaloreAc2).forEach(System.out::println);

        // Cancelling releases the seats
        bookingService.cancel(booking.getBookingId());
        System.out.println("\nAvailability Mumbai -> Bangalore (AC2 only) after cancellation:");
        searchService.search(mumbaiToBangaloreAc2).forEach(System.out::println);
    }
}
