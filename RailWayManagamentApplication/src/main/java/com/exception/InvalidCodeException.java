package com.exception;

public class InvalidCodeException extends RuntimeException {
     public InvalidCodeException(String message)
     {
    	 super(message);
     }
}