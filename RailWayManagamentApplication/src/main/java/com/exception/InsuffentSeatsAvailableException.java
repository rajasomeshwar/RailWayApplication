package com.exception;
public class InsuffentSeatsAvailableException extends RuntimeException {
    public InsuffentSeatsAvailableException(String message) {
        super(message);
    }
}
