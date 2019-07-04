package com.tortoiselala.bean;

import lombok.Data;

import java.util.List;

/**
 * @author tortoiselala
 */
@Data
public class ImResponseBean<T> {
    private String action;
    private String application;
    private Params params;
    private String path;
    private String uri;
    private List<T> entities;
    private int timestamp;
    private int duration;
    private String organization;
    private String applicationName;
    private String cursor;
    private int count;
}

@Data
class Params{
    private List<String> limit;
}