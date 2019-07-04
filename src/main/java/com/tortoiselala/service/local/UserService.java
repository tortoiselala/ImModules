package com.tortoiselala.service.local;

import com.tortoiselala.bean.UserBean;
import com.tortoiselala.exception.RegistrationException;

/**
 * @author tortoiselala
 */
public interface UserService extends BaseService {

    /**
     * 验证用户登录信息
     * @param user
     * @return
     */
    boolean authentication(UserBean user);

    /**
     * 注册新的用户信息
     * @param user
     * @return
     * @throws RegistrationException
     */
    boolean register(UserBean user) throws RegistrationException;

    /**
     * 删除指定用户
     * @param user
     * @return
     */
    boolean delete(UserBean user);
}
