package com.exception;
public class TokenExpiredLoginAgainException extends RuntimeException {
    public TokenExpiredLoginAgainException(String message) {
        super(message);
    }
}