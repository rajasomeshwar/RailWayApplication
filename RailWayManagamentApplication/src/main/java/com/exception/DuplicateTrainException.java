package com.exception;
public class DuplicateTrainException extends RuntimeException {
    public DuplicateTrainException(String message)
    {
   	 super(message);
    }
}