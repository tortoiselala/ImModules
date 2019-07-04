package com.tortoiselala.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户信息封装Bean
 * @author tortoiselala
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserBean extends BaseBean {

    /**
     * 用户ID
     */
    private String uid;
    /**
     * 用户密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    public UserBean() {
    }

    public UserBean(String uid, String password) {
        this.uid = uid;
        this.password = password;
    }
}
