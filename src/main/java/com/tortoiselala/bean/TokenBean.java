package com.tortoiselala.bean;

import com.google.gson.annotations.SerializedName;
import org.springframework.http.HttpStatus;

/**
 * @author tortoiselala
 */
public class TokenBean {
    @SerializedName("status")
    private HttpStatus status;

    @SerializedName("application")
    private String application;

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("expires_in")
    private long expiresIn;

    private long expire;

    public TokenBean() {
    }

    public TokenBean(HttpStatus status, String application, String accessToken, long expiresIn) {
        this.status = status;
        this.application = application;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    private long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;

        this.expire = System.currentTimeMillis() + expiresIn * 1000;
    }

    public boolean isExpired() {
        return expire <= System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "TokenBean{" +
                "status=" + status +
                ", application='" + application + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}
