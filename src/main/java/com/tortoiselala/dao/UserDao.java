package com.tortoiselala.dao;

import com.tortoiselala.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author tortoiselala
 */
public interface UserDao {

    /**
     * 新增用户
     * @param user
     * @return
     */
    int insertNewUser(User user);

    /**
     * 获取指定用户
     * @param uid
     * @return
     */
    User getUserByUid(String uid);

    /**
     * 更新最后登录时间
     * @param uid
     * @param time
     * @return
     */
    int updateLoginTimeByUid(@Param("uid") String uid, @Param("time") long time);

    /**
     * 根据email获取用户
     * @param email
     * @return
     */
    User getUserByEmail(String email);

    /**
     * 删除指定用户
     * @param uid
     * @return
     */
    int deleteUserByUid(String uid);
}
