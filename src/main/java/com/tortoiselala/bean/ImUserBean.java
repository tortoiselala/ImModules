package com.tortoiselala.bean;

import lombok.Data;

/**
 * @author tortoiselala
 */
@Data
public class ImUserBean {
    /**
     * 用户的UUID，标识字段
     */
    private String uuid;
    /**
     * “user”用户类型
     */
    private String type;
    /**
     * 用户名，也就是环信 ID
     */
    private String username;
    /**
     * 用户是否已激活，“true”已激活，“false“封禁，封禁需要通过解禁接口进行解禁，才能正常登录
     */
    private String activated;

    /**
     * 昵称，在 iOS Apns 推送时会使用的昵称，没有设置返则不会返回
     */
    private String nickname;

    /**
     * 消息提醒方式，“true”仅通知，“false“通知以及消息详情，没有设置返则不会返回
     */
    private Boolean notificationNoDisturbing;

    /**
     * 免打扰的设置。“0”代表免打扰关闭，“1”免打扰开启，没有设置返则不会返回
     */
    private Boolean notificationDisplayStyle;

    /**
     * 免打扰的开始时间。数字代表开始时间，例如“8”代表每日8:00开启免打扰，没有设置返则不会返回
     */
    private Integer notificationNoDisturbingStart;

    /**
     * 免打扰的结束时间。数字代表结束时间，例如“18”代表每日18:00关闭免打扰，没有设置返则不会返回
     */
    private Integer notificationNoDisturbingEnd;

    /**
     * 客户端推送证书名称，没有设置返则不会返回
     */
    private String notifierName;

    /**
     * 创建时间
     */
    private Long created;

    /**
     * 修改时间
     */
    private Long modified;
}
