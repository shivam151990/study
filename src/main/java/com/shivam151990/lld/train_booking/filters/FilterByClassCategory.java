package com.shivam151990.lld.train_booking.filters;

import com.shivam151990.lld.train_booking.model.ClassCategory;
import com.shivam151990.lld.train_booking.model.Seat;

public class FilterByClassCategory implements SearchFilter {

    private final ClassCategory category;

    public FilterByClassCategory(ClassCategory category) {
        this.category = category;
    }

    @Override
    public boolean test(Seat seat) {
        return seat.getCategory() == category;
    }
}
