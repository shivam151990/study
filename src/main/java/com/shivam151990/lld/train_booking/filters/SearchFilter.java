package com.shivam151990.lld.train_booking.filters;

import com.shivam151990.lld.train_booking.model.Seat;

public interface SearchFilter {
    boolean test(Seat seat);
}
