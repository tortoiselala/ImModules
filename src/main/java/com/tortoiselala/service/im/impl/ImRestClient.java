package com.tortoiselala.service.im.impl;

import com.google.gson.Gson;
import com.tortoiselala.bean.TokenBean;
import com.tortoiselala.config.ImConfiguration;
import com.tortoiselala.exception.TokenRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author tortoiselala
 */
@Service
public class ImRestClient {

    @Autowired
    ImConfiguration imConfig;

    /**
     * im服务器访问令牌
     */
    private TokenBean token;
    /**
     * 令牌访问出错隔离间隔标志
     */
    private volatile boolean tokenValid = true;
    /**
     * 令牌请求间隔计数器
     */
    private int count = 0;
    /**
     * 令牌重新请求计数器阀值
     */
    private static final int THRESHOLD = 10;

    private static final Object[] LOCK = new Object[0];
    /**
     * im服务器主机地址
     */
    private String server = "http://a1.easemob.com";
    /**
     *
     */
    private RestTemplate rest;
    /**
     * 通用请求头
     */
    private HttpHeaders headers;

    private Logger logger = LoggerFactory.getLogger(ImRestClient.class);

    public ImRestClient() {
        this.rest = new RestTemplate();
        this.rest.setErrorHandler(new CustomRestClientResponseHanlder());
        this.headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
    }

    public ResponseEntity<String> post(String uri, String body) throws TokenRequestException {
        return post(uri, body, null);
    }

    public ResponseEntity<String> post(String uri, String body, HttpHeaders headers) throws TokenRequestException {
        HttpEntity<String> request = requestBuilder(body, headers);

        return exchange(uri, HttpMethod.POST, request);
   }

    public ResponseEntity<String> get(String uri, String body) throws TokenRequestException {
        return get(uri, body, null);
    }

    public ResponseEntity<String> get(String uri, String body, HttpHeaders headers) throws TokenRequestException {
        HttpEntity<String> request = requestBuilder(body, headers);
        return exchange(uri, HttpMethod.GET, request);
    }



    public ResponseEntity<String> put(String uri, String body) throws TokenRequestException {
        return put(uri, body, headers);
    }

    public ResponseEntity<String> put(String uri, String body, HttpHeaders headers) throws TokenRequestException {
        HttpEntity<String> request = requestBuilder(body, headers);
        return exchange(uri, HttpMethod.PUT, request);
    }

    public ResponseEntity<String> delete(String uri, String body) throws TokenRequestException {
        return delete(uri, body, headers);
    }

    public ResponseEntity<String> delete(String uri, String body, HttpHeaders headers) throws TokenRequestException {
        HttpEntity<String> request = requestBuilder(body, headers);
        return exchange(uri, HttpMethod.DELETE, request);
    }

    public ResponseEntity<String> exchange(String uri, HttpMethod method, HttpEntity<String> request){
        ResponseEntity<String> response;
        response = rest.exchange(server + uri, method, request, String.class);

        HttpStatus status = response.getStatusCode();

        if(status.is4xxClientError() || status.is5xxServerError()){
            logger.warn("");
            logger.warn("Server response : " + response);
        }

        logger.debug("Post response" + response);
        return response;
    }

    private HttpEntity<String> requestBuilder(String body, HttpHeaders headers) throws TokenRequestException {
        HttpEntity<String> request;
        if(headers != null){
            headers.set("Authorization", "Bearer " + getToken().getAccessToken());
            headers.set("Content-Type", "application/json");
            headers.set("Accept", "application/json");
            request = new HttpEntity<>(body, headers);
        }else{
            this.headers.set("Authorization", "Bearer " + getToken().getAccessToken());
            request = new HttpEntity<>(body, this.headers);
        }
        return request;
    }

    private TokenBean getToken() throws TokenRequestException {
        TokenBean _token = new TokenBean();
        _token.setAccessToken("YWMtbqpEEqBuEemF36-SLcJqHQAAAAAAAAAAAAAAAAAAAAF-9iKSG8ZDTqbjwKqtlAPdAgMAAAFryqihnABPGgCz3LgvUVnFCLbFK-sY3cUgzuogyZvIjOHeOStkTjjwpw");
        boolean s = true;
        if(s){
            return _token;
        }


        if (!tokenValid || token == null || token.isExpired()) {
            synchronized (LOCK) {
                if (!tokenValid) {
                    if (count++ >= THRESHOLD) {
                        count = 0;
                        token = null;
                        return getToken();
                    } else {
                        throw new TokenRequestException("Already isolated due to continued failure");
                    }
                } else if (token == null || token.isExpired()) {
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Content-Type", "application/json");
                    headers.set("Accept", "application/json");
                    String body = imConfig.generateTokenBody();
                    ResponseEntity<String> response = null;
                    int localCount = 3;
                    while (localCount > 0) {
                        HttpEntity<String> request = new HttpEntity<>(body, headers);
                        response = exchange(imConfig.generateTokenUri(), HttpMethod.POST, request);
                        --localCount;
                        if (response.getStatusCode().is2xxSuccessful()) {
                            break;
                        }
                        logger.warn("Server response " + response.getStatusCode().value() + " will try again in 1 seconds");
                    }
                    if (localCount <= 0) {
                        this.count = 0;
                        logger.error("Get token failed");
                        token = null;
                        tokenValid = false;
                        return new TokenBean(HttpStatus.UNAUTHORIZED,"","",System.currentTimeMillis());
                    }
                    Gson g = new Gson();

                    TokenBean token = g.fromJson(response.getBody(), TokenBean.class);
                    token.setStatus(response.getStatusCode());
                    this.token = token;
                    tokenValid = true;
                    this.count = 0;
                }
            }
        }
        System.out.println("access token" + token.getAccessToken());
        return token;
    }
}
