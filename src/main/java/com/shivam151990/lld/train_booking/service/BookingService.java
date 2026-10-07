package com.shivam151990.lld.train_booking.service;

import com.shivam151990.lld.train_booking.model.Booking;
import com.shivam151990.lld.train_booking.model.BookingRequest;

public interface BookingService {
    Booking book(BookingRequest request);
    void cancel(String bookingId);
}
