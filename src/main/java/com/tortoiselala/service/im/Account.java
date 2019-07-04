package com.tortoiselala.service.im;

/**
 * @author tortoiselala
 */
public interface Account {
    /**
     * 在im服务器上创建新的用户
     * @param uid
     * @param password
     * @return
     */
    boolean createAccount(String uid, String password);

}
