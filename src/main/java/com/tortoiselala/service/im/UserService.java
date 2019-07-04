package com.tortoiselala.service.im;

import com.tortoiselala.bean.ImResponseBean;
import com.tortoiselala.bean.ResponseBean;
import com.tortoiselala.bean.UserPair;
import com.tortoiselala.exception.*;

/**
 * @author tortoiselala
 */
public interface UserService {

    /**
     * 在im服务器注册单个用户
     * @param user
     * @throws TooManayRequestsException
     */
    void register(UserPair user) throws TooManayRequestsException, RequestNotFoundException, BadRequestException, ImServerRequestException, UnauthorizedException, RequestForbiddenException;

    /**
     * 获取用户
     * @param user
     * @return
     */
    ResponseBean<ImResponseBean> getUser(UserPair user) throws BadRequestException, TooManayRequestsException, ImServerRequestException, RequestForbiddenException, RequestNotFoundException, UnauthorizedException;

    /**
     * 删除用户
     * @param user
     */
    void deleteUser(UserPair user) throws BadRequestException, TooManayRequestsException, ImServerRequestException, RequestForbiddenException, RequestNotFoundException, UnauthorizedException;

    /**
     * 更改密码
     * @param user
     */
    void changePassword(UserPair user) throws BadRequestException, TooManayRequestsException, ImServerRequestException, RequestForbiddenException, RequestNotFoundException, UnauthorizedException;

    /**
     * 增加新朋友
     * @param user
     */
    void addNewFriend(UserPair user) throws BadRequestException, TooManayRequestsException, ImServerRequestException, RequestForbiddenException, RequestNotFoundException, UnauthorizedException;

    /**
     * 删除指定好友
     * @param user
     */
    void deleteFriend(UserPair user) throws BadRequestException, TooManayRequestsException, ImServerRequestException, RequestForbiddenException, RequestNotFoundException, UnauthorizedException;

    /**
     * 获取朋友列表
     * @param user
     */
    void getFriendList(UserPair user) throws BadRequestException, TooManayRequestsException, ImServerRequestException, RequestForbiddenException, RequestNotFoundException, UnauthorizedException;
}
