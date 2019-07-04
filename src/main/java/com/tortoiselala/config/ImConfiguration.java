package com.tortoiselala.config;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tortoiselala
 */
public class ImConfiguration {
    public static String TOKEN_VALUE_EASEMOB_CLIENT_ID;

    public static String TOKEN_VALUE_EASEMOB_CLIENT_SECRET;

    public static String TOKEN_VALUE_GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";

    public static String TOKEN_KEY_GRANT_TYPE = "grant_type";

    public static String TOKEN_KEY_CLIENT_ID = "client_id";

    public static String TOKEN_KEY_CLIENT_SECRET = "client_secret";

    public static String TOKEN_KEY_EXPIRES_IN = "expires_in";

    public static String TOKEN_KEY_APPLICATION = "application";

    public static String TOKEN_KEY_ACCESS_TOKEN = "access_token";

    private String clientId;

    private String clientSecret;

    private String orgName;

    private String appName;

    private String appKey;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    final public String generateTokenUri(){
        return "/" + getOrgName() + "/" + getAppName() + "/token";
    }

    final public String generateTokenBody(){

        Map<String, Object> map = new HashMap<>();
        map.put("grant_type", "client_credentials");
        map.put("client_id", getClientId());
        map.put("client_secret", getClientSecret());
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    final public String generateUserUri(){
        return generateUserUri(null);
    }

    final public String generateUserUri(String sub){
        String r = "/" + getOrgName() + "/" + getAppName() + "/users";
        if(sub != null){
            r += sub;
        }
        return r;
    }

    final public String generateFriendUri(String user, String friend){
        return "/" + getOrgName() +
                "/" + getAppName() +
                "/users" +
                "/" + user +
                "/contacts" +
                "/users" +
                "/" + friend;
    }

    final public String generateFriendListUri(String user){
        return "/" + getOrgName() +
                "/" + getAppName() +
                "/users" +
                "/" + user +
                "/contacts" +
                "/users";
    }
}

