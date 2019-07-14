package com.tortoiselala.bean;

import lombok.Data;

/**
 * @author tortoiselala
 */
@Data
public class ProcessResultBean<T> {
    private boolean success;
    private String message;
    private T data;
}
