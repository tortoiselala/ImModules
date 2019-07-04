package com.tortoiselala.service.im;

import com.tortoiselala.bean.TokenBean;
import com.tortoiselala.bean.UserPair;

/**
 * @author tortoiselala
 */
public interface Base {
    /**
     * 向im服务器申请token
     * @return
     */
    TokenBean getToken();

    /**
     * 在im服务器注册单个用户
     * @param userPairs
     * @return
     */
    boolean register(UserPair userPairs);

    /**
     * 获取用户
     * @param user
     * @return
     */
    boolean getUser(UserPair user);

    /**
     * 删除用户
     * @param user
     * @return
     */
    boolean deleteUser(UserPair user);

    /**
     * 更改密码
     * @param user
     * @return
     */
    boolean changePassword(UserPair user);

    /**
     * 增加新朋友
     * @param user
     * @return
     */
    boolean addNewFriend(UserPair user);

    /**
     * 删除指定好友
     * @param user
     * @return
     */
    boolean deleteFriend(UserPair user);

    /**
     * 获取朋友列表
     * @param user
     * @return
     */
    boolean getFriendList(UserPair user);


}
