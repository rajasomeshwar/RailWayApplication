package com.exception;
public class NoSuchTrainIdFoundException extends RuntimeException {
    public NoSuchTrainIdFoundException(String message) {
        super(message);
    }
}