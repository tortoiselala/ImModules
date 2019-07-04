package com.tortoiselala.bean;

import lombok.Data;

/**
 * 封装im服务器发生的异常信息
 * @author tortoiselala
 */
@Data
public class ImBadResponseMessageBean {
    private String error;
    private String exception;
    private int timestamp;
    private int duration;
    private String errorDescription;
}