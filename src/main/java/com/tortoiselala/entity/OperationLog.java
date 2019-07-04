package com.tortoiselala.entity;

import lombok.Data;

/**
 * @author tortoiselala
 */
@Data
public class OperationLog {

    /**
     * 用户操作日志主键
     */
    private long id;

    /**
     * 日志名称
     */
    private String logName;

    /**
     * 用户id
     */
    private String uid;

    /**
     * api名称
     */
    private String api;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 创建时间
     */
    private long createTime;

    /**
     * 是否执行成功(0失败1成功)
     */
    private int succeed;

    /**
     * 具体消息备注
     */
    private String message;
}
