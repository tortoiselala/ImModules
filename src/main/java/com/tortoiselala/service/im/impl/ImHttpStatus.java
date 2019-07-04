package com.tortoiselala.service.im.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * @author tortoiselala
 */
public enum ImHttpStatus {

    ;

    private final int value;

    private final String reasonPhrase;


    ImHttpStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }


    /**
     * Return the integer value of this status code.
     */
    public int value() {
        return this.value;
    }

    /**
     * Return the reason phrase of this status code.
     */
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }


    /**
     * Return a string representation of this status code.
     */
    @Override
    public String toString() {
        return this.value + " " + name();
    }


    /**
     * Return the enum constant of this type with the specified numeric value.
     * @param statusCode the numeric value of the enum to be returned
     * @return the enum constant with the specified numeric value
     * @throws IllegalArgumentException if this enum has no constant for the specified numeric value
     */
    public static ImHttpStatus valueOf(int statusCode) {
        ImHttpStatus status = resolve(statusCode);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
        }
        return status;
    }

    /**
     * Resolve the given status code to an {@code ImHttpStatus}, if possible.
     * @param statusCode the HTTP status code (potentially non-standard)
     * @return the corresponding {@code ImHttpStatus}, or {@code null} if not found
     * @since 5.0
     */
    @Nullable
    public static ImHttpStatus resolve(int statusCode) {
        for (ImHttpStatus status : values()) {
            if (status.value == statusCode) {
                return status;
            }
        }
        return null;
    }

}
