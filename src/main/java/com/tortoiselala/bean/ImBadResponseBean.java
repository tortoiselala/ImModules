package com.tortoiselala.bean;

import lombok.Data;

/**
 * @author tortoiselala
 */
@Data
public class ImBadResponseBean {
    private String error;

    private long timestamp;

    private int duration;

    private String exception;

    private String errorDescription;

}
