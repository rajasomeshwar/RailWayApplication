package com.exception;

public class NoTrainThereInThatPathException extends RuntimeException {
    public NoTrainThereInThatPathException(String message) {
        super(message);
    }
}