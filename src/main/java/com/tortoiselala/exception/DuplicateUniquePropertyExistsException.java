package com.tortoiselala.exception;

/**
 * @author tortoiselala
 */
public class DuplicateUniquePropertyExistsException extends Exception{
    public DuplicateUniquePropertyExistsException() {
    }

    public DuplicateUniquePropertyExistsException(String message) {
        super(message);
    }

    public DuplicateUniquePropertyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateUniquePropertyExistsException(Throwable cause) {
        super(cause);
    }

    public DuplicateUniquePropertyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
