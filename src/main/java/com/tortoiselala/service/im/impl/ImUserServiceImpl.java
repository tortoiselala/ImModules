package com.tortoiselala.service.im.impl;

import com.google.gson.*;
import com.tortoiselala.bean.FriendListBean;
import com.tortoiselala.bean.ImBadResponseBean;
import com.tortoiselala.bean.ImUserBean;
import com.tortoiselala.bean.ProcessResultBean;
import com.tortoiselala.config.ImConfiguration;
import com.tortoiselala.exception.TokenRequestException;
import com.tortoiselala.service.im.ImUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author tortoiselala
 */
@Service
public class ImUserServiceImpl implements ImUserService {

    @Autowired
    ImRestClient imRestClient;

    @Autowired
    ImConfiguration imConfig;

    Logger logger = LoggerFactory.getLogger(ImUserServiceImpl.class);

    @Override
    public ProcessResultBean register(String uid, String password) {
        Gson gson = new Gson();
        Map<String, Object> bodyMap = new HashMap<>();
        String uri = "/" + imConfig.getOrgName() + "/" + imConfig.getAppName() + "/users";
        String body = null;

        bodyMap.put("username", uid);
        bodyMap.put("password", password);

        body = gson.toJson(bodyMap);
        return getResponseBean(uri, body, HttpMethod.POST);
    }

    @Override
    public ProcessResultBean<ImUserBean> getUser(String uid) {

        String uri = "/" + imConfig.getOrgName() + "/" + imConfig.getAppName() + "/users/" + uid;
        ResponseEntity<String> response;
        ProcessResultBean<ImUserBean> result = new ProcessResultBean<>();
        ImUserBean user;
        try {
            response = imRestClient.post(uri, "");
            getImUserResponseBean(response, result);
        } catch (Exception e) {
            result.setSuccess(false);
            logger.error(e.getMessage());
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @Override
    public ProcessResultBean<ImUserBean> deleteUser(String uid) {
        String uri = "/" + imConfig.getOrgName() + "/" + imConfig.getAppName() + "/users/" + uid;
        ResponseEntity<String> response;
        ProcessResultBean<ImUserBean> result = new ProcessResultBean<>();
        try {
            response = imRestClient.delete(uri, "");
            getImUserResponseBean(response, result);
        } catch (Exception e) {
            result.setSuccess(false);
            logger.error(e.getMessage());
            result.setMessage(e.getMessage());
        }
        return result;
    }


    @Override
    public ProcessResultBean changePassword(String uid, String newPassword) {
        String uri = "/" + imConfig.getOrgName() + "/" + imConfig.getAppName() + "/user/" + uid + "/password";
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("newpassword", newPassword);
        Gson gson = new Gson();
        String body = gson.toJson(bodyMap);
        return getResponseBean(uri, body, HttpMethod.POST);
    }

    @Override
    public ProcessResultBean addNewFriend(String uid, String friendUid) {
        // /{org_name}/{app_name}/users/{owner_username}/contacts/users/{friend_username}
        String uri = "/" + imConfig.getOrgName() + "/" + imConfig.getAppName() + "/users/" + uid + "/contacts/users/" + friendUid;

        String body = "";
        return getResponseBean(uri, body, HttpMethod.POST);

    }

    @Override
    public ProcessResultBean deleteFriend(String uid, String friendUid) {
        // /{org_name}/{app_name}/users/{owner_username}/contacts/users/{friend_username}
        String uri = "/" + imConfig.getOrgName() + "/" + imConfig.getAppName() + "/users/" + uid + "/contacts/users/" + friendUid;

        String body = "";
        return getResponseBean(uri, body, HttpMethod.DELETE);
    }

    @Override
    public ProcessResultBean<FriendListBean> getFriendList(String uid) {
        // /{org_name}/{app_name}/users/{owner_username}/contacts/users
        String uri = "/" + imConfig.getOrgName() + "/" + imConfig.getAppName() + "/users/" + uid + "/contacts/users";
        String body = "";
        Gson gson = new Gson();
        ResponseEntity<String> response = null;
        ProcessResultBean<FriendListBean> result = new ProcessResultBean<>();
        try {
            response = imRestClient.get(uri, body);
            HttpStatus status = response.getStatusCode();
            if(status.is2xxSuccessful()){
                JsonParser parser = new JsonParser();

                JsonObject object = parser.parse(Objects.requireNonNull(response.getBody())).getAsJsonObject();

                JsonArray array = object.getAsJsonArray("data");

                FriendListBean friends = new FriendListBean();
                friends.setUid(uid);
                friends.setCount(array.size());

                List<String> list = new LinkedList<>();
                friends.setFriends(list);
                for(int i = 0; i < array.size(); ++i){
                    String elem = gson.fromJson(array.get(i), String.class);
                    list.add(elem);
                }
                result.setSuccess(true);
                result.setData(friends);
            }else{
                ImBadResponseBean error = gson.fromJson(response.getBody(), ImBadResponseBean.class);
                result.setSuccess(false);
                result.setMessage("Server response " + response.getStatusCode() + ", message:" + error.getErrorDescription());
            }
        } catch (TokenRequestException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    private void getImUserResponseBean(ResponseEntity<String> response, ProcessResultBean<ImUserBean> result) throws Exception {
        Gson gson = new Gson();
        HttpStatus status = response.getStatusCode();
        ImUserBean user;
        if(status.is2xxSuccessful()){
            // {"path":"/users","uri":"https://a1.easemob.com/1103190701090583/imm/users/register",
            // "timestamp":1562474651618,
            // tities":
            // [{"notification_no_disturbing":null,
            // "created":1562473560727,
            // "nickname":"",
            // "notification_display_style":null,
            // "modified":1562473560727,
            // "notification_no_disturbing_start":null,
            // "notification_no_disturbing_end":null,
            // "type":"user","uuid":"53150010-a06f-11e9-90d6-9d0977407353",
            // "username":"register","activated":true}],
            // "count":1,
            // "action":"post","duration":170}
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(Objects.requireNonNull(response.getBody())).getAsJsonObject();
            JsonArray userJsonArray = jsonObject.getAsJsonArray("entities");
            JsonElement el = userJsonArray.get(0);
            user = gson.fromJson(el, ImUserBean.class);
            result.setSuccess(true);
            result.setData(user);
        }else{
            ImBadResponseBean error = gson.fromJson(response.getBody(), ImBadResponseBean.class);
            throw new Exception("Server response " + response.getStatusCode() + ", message:" + error.getErrorDescription());
        }
    }

    private ProcessResultBean getResponseBean(String uri, String body, HttpMethod method) {
        Gson gson = new Gson();
        ResponseEntity<String> response = null;
        ProcessResultBean  result = new ProcessResultBean();
        try {

            if(method == HttpMethod.POST){
                response = imRestClient.post(uri, body);
            }else if(method == HttpMethod.DELETE){
                response = imRestClient.delete(uri, body);
            }


            HttpStatus status = response.getStatusCode();
            if(status.is2xxSuccessful()){

                result.setSuccess(true);
                return result;
            }else{
                ImBadResponseBean error = gson.fromJson(response.getBody(), ImBadResponseBean.class);
                throw new Exception("Server response " + response.getStatusCode() + ", message:" + error.getErrorDescription() + ", exception:" + error.getException());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            return result;

        }
    }
}
