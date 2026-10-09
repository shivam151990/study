package com.shivam151990.lld.amazon_locker.exception;

public class NoStorageSpaceException extends RuntimeException {
    public NoStorageSpaceException(String message) {
        super(message);
    }
}
