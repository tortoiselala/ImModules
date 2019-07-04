package com.tortoiselala.bean;

import lombok.Data;

/**
 * @author tortoiselala
 */
@Data
public class RegistrationResultBean <T> {
    private boolean status;

    private String message;

    private T data;
}
