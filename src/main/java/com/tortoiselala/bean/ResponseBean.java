package com.tortoiselala.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 响应Bean，返回请求结果
 * @author tortoiselala
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResponseBean <T> extends BaseBean {
    /**
     * code 响应状态码，遵循标准HttpStatus样式
     */
    private int code;
    /**
     * message 请求失败时有效
     */
    private String message;

    /**
     * data 请求成功，数据
     */
    private T data;

    public ResponseBean() {
    }

    public ResponseBean(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}

