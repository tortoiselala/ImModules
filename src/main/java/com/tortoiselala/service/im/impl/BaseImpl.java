package com.tortoiselala.service.im.impl;

import com.google.gson.Gson;
import com.tortoiselala.bean.TokenBean;
import com.tortoiselala.bean.UserPair;
import com.tortoiselala.config.ImConfiguration;
import com.tortoiselala.exception.ServerInnerErrorException;
import com.tortoiselala.service.im.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tortoiselala
 */
@Service
public class BaseImpl implements Base {

    private TokenBean token;

    private volatile boolean valid = true;

    private int count = 0;

    private static final int THRESHOLD = 10;

    private static final Object[] LOCK = new Object[0];

    private final Logger logger = LoggerFactory.getLogger(BaseImpl.class);

    @Autowired
    ImConfiguration imConfiguration;

    @Override
    public TokenBean getToken() {

        if (!valid || token == null || token.isExpired()) {
            synchronized (LOCK) {
                if (!valid) {
                    if (count++ >= THRESHOLD) {
                        count = 0;
                        valid = true;
                    } else {
                        throw new ServerInnerErrorException("Already isolated due to continued failure");
                    }
                } else if (token == null || token.isExpired()) {
                    RestClient client = new RestClient();
                    ResponseEntity<String> response = null;
                    int count = 3;
                    while (count > 0) {
                        response = client.post(imConfiguration.generateTokenUri(), imConfiguration.generateTokenBody());
                        --count;
                        if (response.getStatusCode().is2xxSuccessful()) {
                            break;
                        }
                        logger.warn("Server response " + response.getStatusCode().value() + " will try again in 1 seconds");
                    }
                    if (count <= 0) {
                        this.count = 0;
                        logger.error("Get token failed");
                        token = null;
                        valid = false;
                    }
                    Gson g = new Gson();

                    TokenBean token = g.fromJson(response.getBody(), TokenBean.class);
                    token.setStatus(response.getStatusCode());
                    this.token = token;
                    valid = true;
                    this.count = 0;
                }
            }
        }
        return token;
    }

    @Override
    public boolean register(UserPair userPair) {
        HttpHeaders headers = header(null);
        Gson gson = new Gson();
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("username", userPair.getUsername());
        bodyMap.put("password", userPair.getPassword());
        String body = gson.toJson(bodyMap);

        RestClient client = new RestClient();

        ResponseEntity<String> response = client.post(imConfiguration.generateUserUri(), body, headers);

        userPair.setResponse(response);

        HttpStatus status = response.getStatusCode();
        return status.is2xxSuccessful();
    }

    @Override
    public boolean getUser(UserPair user) {
        HttpHeaders headers = header(null);
        String uri = imConfiguration.generateUserUri("/" + user.getUsername());

        RestClient client = new RestClient();
        ResponseEntity<String> response = client.get(uri, headers);

        user.setResponse(response);
        HttpStatus status = response.getStatusCode();
        return status.is2xxSuccessful();
    }

    @Override
    public boolean deleteUser(UserPair user){
        HttpHeaders headers = header(null);
        String uri = imConfiguration.generateUserUri("/" + user.getUsername());
        RestClient client = new RestClient();
        ResponseEntity<String> response = client.delete(uri, headers);

        user.setResponse(response);
        HttpStatus status = response.getStatusCode();
        return status.is2xxSuccessful();
    }

    @Override
    public boolean changePassword(UserPair user) {
        HttpHeaders headers = header(null);
        String uri = imConfiguration.generateUserUri("/" + user.getUsername() + "/password");
        RestClient client = new RestClient();
        Map<String, Object> bodyMap = new HashMap<>();
        Gson gson = new Gson();
        bodyMap.put("newpassword", user.getNewPassword());
        String body = gson.toJson(bodyMap);
        ResponseEntity<String> response = client.put(uri, body, headers);
        user.setResponse(response);
        HttpStatus status = response.getStatusCode();
        return status.is2xxSuccessful();
    }

    @Override
    public boolean addNewFriend(UserPair user) {
        HttpHeaders headers = header(null);
        String uri = imConfiguration.generateFriendUri(user.getUsername(), user.getFriend());
        RestClient client = new RestClient();
        ResponseEntity<String> response = client.post(uri, "", headers);

        user.setResponse(response);
        HttpStatus status = response.getStatusCode();
        return status.is2xxSuccessful();
    }

    @Override
    public boolean deleteFriend(UserPair user) {

        HttpHeaders headers = header(null);
        String uri = imConfiguration.generateFriendUri(user.getUsername(), user.getFriend());
        RestClient client = new RestClient();
        ResponseEntity<String> response = client.delete(uri, "", headers);

        user.setResponse(response);
        HttpStatus status = response.getStatusCode();
        return status.is2xxSuccessful();
    }

    @Override
    public boolean getFriendList(UserPair user) {
        HttpHeaders headers = header(null);
        String uri = imConfiguration.generateFriendListUri(user.getUsername());
        RestClient client = new RestClient();
        ResponseEntity<String> response = client.get(uri, "", headers);

        user.setResponse(response);
        HttpStatus status = response.getStatusCode();
        return status.is2xxSuccessful();
    }

    private HttpHeaders header(Map<String, String> map){
//        TokenBean curToken = getToken();
        HttpHeaders headers = new HttpHeaders();
        if(map != null){
            for(Map.Entry<String, String> entry : map.entrySet()){
                headers.set(entry.getKey(), entry.getValue());
            }
        }
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
//        headers.set("Authorization", "Bearer " + curToken.getAccessToken());
        headers.set("Authorization", "Bearer YWMtYt6LoJ4xEemJqNciOHbLYAAAAAAAAAAAAAAAAAAAAAF-9iKSG8ZDTqbjwKqtlAPdAgMAAAFru_0d1ABPGgBjW8ZcB_3soiXu4hhvlsy8X-JAFDzSp_DcK6ABqZathw");
        return headers;
    }
}
