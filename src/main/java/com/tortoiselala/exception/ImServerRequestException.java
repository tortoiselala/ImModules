package com.tortoiselala.exception;

/**
 * @author tortoiselala
 */
public class ImServerRequestException extends Exception {
    public ImServerRequestException() {
    }

    public ImServerRequestException(String message) {
        super(message);
    }

    public ImServerRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImServerRequestException(Throwable cause) {
        super(cause);
    }

    public ImServerRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
