package com.tortoiselala.exception;

/**
 * @author tortoiselala
 */
public class TooManayRequestsException extends Exception {

    public TooManayRequestsException() {
    }

    public TooManayRequestsException(String message) {
        super(message);
    }

    public TooManayRequestsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManayRequestsException(Throwable cause) {
        super(cause);
    }

    public TooManayRequestsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
