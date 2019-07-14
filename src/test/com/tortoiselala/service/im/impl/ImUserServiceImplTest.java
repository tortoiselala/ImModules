package com.tortoiselala.service.im.impl;

import com.tortoiselala.bean.FriendListBean;
import com.tortoiselala.bean.ImUserBean;
import com.tortoiselala.bean.ProcessResultBean;
import com.tortoiselala.service.im.ImUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author tortoiselala
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-*.xml"})
public class ImUserServiceImplTest {
    @Autowired
    ImUserService userService;

    String uid = "register";
    String password = "register";
    String existsFriendUid = "1546191727";
    String notExistsFriendUid = "fdfdfaf";

    @Test
    public void register() {
        ProcessResultBean result = userService.register(uid, password);
        System.out.println(result);
    }


    @Test
    public void getUser() {
        ProcessResultBean<ImUserBean> result = userService.getUser(uid);
        System.out.println(result);
    }

    @Test
    public void deleteUser() {
        ProcessResultBean<ImUserBean> result = userService.deleteUser(uid);
        System.out.println(result);
    }

    @Test
    public void changePassword() {
        ProcessResultBean result = userService.changePassword(uid, "newpassword");
        System.out.println(result);
    }

    @Test
    public void addNewFriend() {
        ProcessResultBean result = userService.addNewFriend(uid, existsFriendUid);
        System.out.println(result);
        result = userService.addNewFriend(uid, notExistsFriendUid);
        System.out.println(result);
    }

    @Test
    public void deleteFriend() {
        ProcessResultBean result = userService.deleteFriend(uid, existsFriendUid);
        System.out.println(result);
        result = userService.deleteFriend(uid, notExistsFriendUid);
        System.out.println(result);
    }

    @Test
    public void getFriendList() {
        ProcessResultBean<FriendListBean> result = userService.getFriendList(uid);
        System.out.println(result);
        result = userService.getFriendList(existsFriendUid);
        System.out.println(result);
    }
}