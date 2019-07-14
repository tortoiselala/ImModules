package com.tortoiselala.service.im;

import com.tortoiselala.bean.FriendListBean;
import com.tortoiselala.bean.ImUserBean;
import com.tortoiselala.bean.ProcessResultBean;

/**
 * @author tortoiselala
 */
public interface ImUserService {
    /**

    /**
     * 在im服务器注册单个用户
     * @param uid
     * @param password
     * @return
     */
    ProcessResultBean register(String uid, String password);

    /**
     * 获取用户
     * @param uid
     * @return
     */
    ProcessResultBean getUser(String  uid);

    /**
     * 删除用户
     * @param uid
     * @return
     */
    ProcessResultBean<ImUserBean> deleteUser(String uid);

    /**
     * 更改密码
     * @param uid
     * @param newPassword
     * @return
     */
    ProcessResultBean changePassword(String  uid, String newPassword);

    /**
     * 增加新朋友
     * @param uid
     * @param friendUid
     * @return
     */
    ProcessResultBean addNewFriend(String uid, String friendUid);
    /**
     * 删除指定好友
     * @return
     */
    ProcessResultBean deleteFriend(String uid, String friendUid);

    /**
     * 获取朋友列表
     * @param uid
     * @return
     */
    ProcessResultBean<FriendListBean> getFriendList(String uid);
}
