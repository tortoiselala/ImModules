package com.tortoiselala.service.local.impl;

import com.tortoiselala.bean.UserBean;
import com.tortoiselala.dao.UserDao;
import com.tortoiselala.entity.User;
import com.tortoiselala.exception.RegistrationException;
import com.tortoiselala.service.local.UserService;
import com.tortoiselala.util.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tortoiselala
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public boolean authentication(UserBean user) {
        User userInfo = userDao.getUserByUid(user.getUid());
        return userInfo != null
                && Md5Utils.passwordConvert(user.getPassword(), userInfo.getSalt()).
                equals(userInfo.getPassword());
    }

    @Override
    public boolean register(UserBean user) throws RegistrationException {
        User check = userDao.getUserByUid(user.getUid());
        if(check != null){
            throw new RegistrationException("Uid repeat");
        }

        check = userDao.getUserByEmail(user.getEmail());
        if(check != null){
            throw new RegistrationException("The email has been registered");
        }

        User r = new User();
        r.setUid(user.getUid());
        r.setUsername(user.getUid());
        r.setSalt(Md5Utils.randomSalt());
        r.setPassword(Md5Utils.passwordConvert(user.getPassword(), r.getSalt()));
        r.setRealName("");
        r.setPhone("");
        r.setEmail("");
        r.setSex(1);
        r.setStatus(1);
        r.setCreateTime(System.currentTimeMillis());
        r.setUpdateTime(System.currentTimeMillis());
        r.setCreateWhere(1);

        int result = userDao.insertNewUser(r);

        return result > 0;
    }

    @Override
    public boolean delete(UserBean user) {
        return userDao.deleteUserByUid(user.getUid()) > 0;
    }
}
