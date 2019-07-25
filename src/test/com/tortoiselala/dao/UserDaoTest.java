package com.tortoiselala.dao;

import com.tortoiselala.entity.User;
import com.tortoiselala.util.Md5Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author tortoiselala
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class UserDaoTest {

    @Autowired
    UserDao userDao;

    @Test
    public void insertNewUser() {
        User user = new User();
        String password = "yangminwu";
        String salt = "abcderf";
        user.setUid("1546191727");
        user.setUsername("wym");
        user.setPassword(Md5Utils.convert16(password + salt));
        user.setSalt(salt);
        user.setRealName("wym");
        user.setPhone("17319005295");
        user.setEmail("tortoiselala@gmalil.com");
        user.setSex(0);
        user.setStatus(1);
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        user.setCreateWhere(1);

        System.out.println(userDao.insertNewUser(user));
    }

    @Test
    public void getUserByUid() {
        User user = userDao.getUserByUid("1546191727");
        System.out.println(user);
    }

    @Test
    public void updateLoginTimeByUid() {
        System.out.println(userDao.updateLoginTimeByUid("1546191727", System.currentTimeMillis()));
    }
}