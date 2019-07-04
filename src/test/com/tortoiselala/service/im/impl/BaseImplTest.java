package com.tortoiselala.service.im.impl;

import com.tortoiselala.bean.TokenBean;
import com.tortoiselala.bean.UserPair;
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
public class BaseImplTest {
    @Autowired
    BaseImpl baseService;

    @Test
    public void getToken() {
        TokenBean token = baseService.getToken();
        System.out.println(token);
    }

    @Test
    public void register() {
        UserPair user = new UserPair();
        user.setUsername("test_register");
        user.setPassword("test_register");
        baseService.register(user);
        System.out.println(user.getResponse().getBody());
    }

    @Test
    public void getUser() {
        UserPair user = new UserPair();
        user.setUsername("test_register");
        user.setPassword("test_register");
        baseService.getUser(user);
        System.out.println(user.getResponse());
    }

    @Test
    public void deleteUser() {
        UserPair user = new UserPair();
        user.setUsername("test_register");
        user.setPassword("test_register");
        //baseService.deleteUser(user);
        System.out.println(user.getResponse());
    }

    @Test
    public void changePassword() {
        UserPair user = new UserPair();
        user.setUsername("test_register");
        user.setPassword("test_register");
        user.setNewPassword("new_password");
        baseService.deleteUser(user);
        System.out.println(user.getResponse());
    }

    @Test
    public void addNewFriend() {
        UserPair user = new UserPair();
        user.setUsername("test_register");
        user.setPassword("test_register");
        user.setFriend("1546191727");
        baseService.addNewFriend(user);
        System.out.println(user.getResponse());
    }

    @Test
    public void deleteFriend() {
        UserPair user = new UserPair();
        user.setUsername("test_register");
        user.setPassword("test_register");
        user.setFriend("1546191727");
        baseService.deleteFriend(user);
        System.out.println(user.getResponse());
    }

    @Test
    public void getFriendList() {
        UserPair user = new UserPair();
        user.setUsername("test_register");
        user.setPassword("test_register");
        user.setFriend("1546191727");
        baseService.getFriendList(user);
        System.out.println(user.getResponse());
    }
}