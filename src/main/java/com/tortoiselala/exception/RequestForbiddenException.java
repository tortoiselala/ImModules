package com.tortoiselala.exception;

/**
 * @author tortoiselala
 */
public class RequestForbiddenException extends Exception {
    public RequestForbiddenException() {
    }

    public RequestForbiddenException(String message) {
        super(message);
    }

    public RequestForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestForbiddenException(Throwable cause) {
        super(cause);
    }

    public RequestForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
