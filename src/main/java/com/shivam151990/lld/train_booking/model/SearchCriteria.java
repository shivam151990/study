package com.shivam151990.lld.train_booking.model;

import com.shivam151990.lld.train_booking.filters.SearchFilter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchCriteria {
    private final String source;
    private final String destination;
    private final LocalDate date;
    private final List<SearchFilter> filters;

    private SearchCriteria(Builder builder) {
        this.source = builder.source;
        this.destination = builder.destination;
        this.date = builder.date;
        this.filters = List.copyOf(builder.filters);
    }

    public static Builder builder(String source, String destination, LocalDate date) {
        return new Builder(source, destination, date);
    }

    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public LocalDate getDate() { return date; }
    public List<SearchFilter> getFilters() { return filters; }

    public static class Builder {
        private final String source;
        private final String destination;
        private final LocalDate date;
        private final List<SearchFilter> filters = new ArrayList<>();

        private Builder(String source, String destination, LocalDate date) {
            this.source = Objects.requireNonNull(source, "source is required");
            this.destination = Objects.requireNonNull(destination, "destination is required");
            this.date = Objects.requireNonNull(date, "date is required");
        }

        public Builder filter(SearchFilter filter) {
            filters.add(Objects.requireNonNull(filter));
            return this;
        }

        public SearchCriteria build() {
            if (source.equals(destination)) {
                throw new IllegalArgumentException("source and destination must differ");
            }
            return new SearchCriteria(this);
        }
    }
}
