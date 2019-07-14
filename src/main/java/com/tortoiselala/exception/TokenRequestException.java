package com.tortoiselala.exception;

/**
 * @author tortoiselala
 */
public class TokenRequestException extends Exception{
    public TokenRequestException() {
    }

    public TokenRequestException(String message) {
        super(message);
    }

    public TokenRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenRequestException(Throwable cause) {
        super(cause);
    }

    public TokenRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
