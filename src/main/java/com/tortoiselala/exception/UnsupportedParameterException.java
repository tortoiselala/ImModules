package com.tortoiselala.exception;

/**
 * @author tortoiselala
 */
public class UnsupportedParameterException extends RuntimeException{
    public UnsupportedParameterException(String message) {
        super(message);
    }

    public UnsupportedParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}
