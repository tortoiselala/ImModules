package com.tortoiselala.exception;

/**
 * @author tortoiselala
 */
public class ServerInnerErrorException extends RuntimeException {
    public ServerInnerErrorException() {
    }

    public ServerInnerErrorException(String message) {
        super(message);
    }

    public ServerInnerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
