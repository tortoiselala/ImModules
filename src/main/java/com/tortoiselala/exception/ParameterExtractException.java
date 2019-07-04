package com.tortoiselala.exception;

/**
 * @author tortoiselala
 */
public class ParameterExtractException extends Exception {
    public ParameterExtractException() {
    }

    public ParameterExtractException(String message) {
        super(message);
    }

    public ParameterExtractException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterExtractException(Throwable cause) {
        super(cause);
    }

    public ParameterExtractException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
