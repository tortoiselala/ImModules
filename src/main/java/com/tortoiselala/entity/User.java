package com.tortoiselala.entity;

import lombok.Data;

/**
 * @author tortoiselala
 */
@Data
public class User {

    /**
     * uid,用户账号,主键
     */
    private String uid;
    /**
     * 用户名(nick_name)
     */
    private String username;
    /**
     * 密码(MD5(密码+盐))
     */
    private String password;
    /**
     * 盐
     */
    private String salt;
    /**
     * 用户真名
     */
    private String realName;
    /**
     * 电话号码(唯一)
     */
    private String phone;
    /**
     * 邮件地址(唯一)
     */
    private String email;
    /**
     * 性别(1.男 2.女)
     */
    private int sex;
    /**
     * 账户状态(1.正常 2.锁定 3.删除 4.非法)
     */
    private int status;
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 更新时间
     */
    private long updateTime;
    /**
     * 创建来源(1.web 2.android 3.ios 4.win 5.macos 6.ubuntu)
     */
    private int createWhere;
}
