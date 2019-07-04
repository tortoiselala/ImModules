package com.tortoiselala.bean;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author tortoiselala
 */
@Data
public class ProcessResultBean {
    private boolean success;
    private HttpStatus status;
    private String message;
}
