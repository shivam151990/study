package com.shivam151990.lld.reviews_and_ratings.exception;

public class RatingExistsForUserException extends RuntimeException {
    public RatingExistsForUserException(String message) {
        super(message);
    }
}
