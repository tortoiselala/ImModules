package com.tortoiselala.service.im.impl;

import com.google.gson.Gson;
import com.tortoiselala.bean.*;
import com.tortoiselala.exception.*;
import com.tortoiselala.service.im.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author tortoiselala
 */
//@Service
public class UserServiceImpl implements UserService {

    @Autowired
    BaseImpl baseImpl;

    @Override
    public void register(UserPair user) throws TooManayRequestsException, RequestNotFoundException, BadRequestException, ImServerRequestException, UnauthorizedException, RequestForbiddenException {
        baseImpl.register(user);
        responseHandler(user.getResponse());
    }

    @Override
    public ResponseBean<ImResponseBean> getUser(UserPair user) throws BadRequestException, TooManayRequestsException, ImServerRequestException, RequestForbiddenException, RequestNotFoundException, UnauthorizedException {
        baseImpl.getUser(user);
        responseHandler(user.getResponse());
        Gson gson = new Gson();
        ImResponseBean<ImUserBean> re = gson.fromJson(user.getResponse().getBody(), ImResponseBean.class);
        ResponseBean<ImResponseBean<ImUserBean>>
    }

    @Override
    public void deleteUser(UserPair user) throws BadRequestException, TooManayRequestsException, ImServerRequestException, RequestForbiddenException, RequestNotFoundException, UnauthorizedException {
        baseImpl.deleteUser(user);
        responseHandler(user.getResponse());
    }

    @Override
    public void changePassword(UserPair user) throws BadRequestException, TooManayRequestsException, ImServerRequestException, RequestForbiddenException, RequestNotFoundException, UnauthorizedException {
        baseImpl.changePassword(user);
        responseHandler(user.getResponse());
    }

    @Override
    public void addNewFriend(UserPair user) throws BadRequestException, TooManayRequestsException, ImServerRequestException, RequestForbiddenException, RequestNotFoundException, UnauthorizedException {
        baseImpl.addNewFriend(user);
        responseHandler(user.getResponse());
    }

    @Override
    public void deleteFriend(UserPair user) throws BadRequestException, TooManayRequestsException, ImServerRequestException, RequestForbiddenException, RequestNotFoundException, UnauthorizedException {
        baseImpl.deleteFriend(user);
        responseHandler(user.getResponse());
    }

    @Override
    public void getFriendList(UserPair user) throws BadRequestException, TooManayRequestsException, ImServerRequestException, RequestForbiddenException, RequestNotFoundException, UnauthorizedException {
        baseImpl.getFriendList(user);
        responseHandler(user.getResponse());
    }

    private void responseHandler(ResponseEntity<String> response) throws TooManayRequestsException, UnauthorizedException, BadRequestException, RequestForbiddenException, RequestNotFoundException, ImServerRequestException {
        HttpStatus status = response.getStatusCode();
        int value = status.value();

        if(status.is2xxSuccessful()){
            return;
        }
        String responseBody = response.getBody();
        ImBadResponseMessageBean responseMessage = new Gson().fromJson(responseBody, ImBadResponseMessageBean.class);
        // 400
        if(value == HttpStatus.BAD_REQUEST.value()){
            throw new BadRequestException(responseMessage.getErrorDescription());
        }

        // 429, 503
        if(value == HttpStatus.TOO_MANY_REQUESTS.value() || value == HttpStatus.SERVICE_UNAVAILABLE.value()){
            throw new TooManayRequestsException(responseMessage.getErrorDescription());
        }

        // 401
        if(value == HttpStatus.UNAUTHORIZED.value()){
            throw new UnauthorizedException(responseMessage.getErrorDescription());
        }

        // 403
        if(value == HttpStatus.FORBIDDEN.value()){
            throw new RequestForbiddenException(responseMessage.getErrorDescription());
        }

        // 404
        if(value == HttpStatus.NOT_FOUND.value()){
            throw new RequestNotFoundException(responseMessage.getErrorDescription());
        }

        //
        throw new ImServerRequestException(responseMessage.getErrorDescription());


    }
}
