package com.tortoiselala.service.im.impl;

import com.google.gson.Gson;
import com.tortoiselala.bean.TokenBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author tortoiselala
 */
public class RestClientImplTest {
    public static void main(String[] args) {
        String response = "{\"application\":\"7ef62292-1bc6-434e-a6e3-c0aaad9403dd\",\"access_token\":\"YWMtTm6Bgp0-EemdR6FGMYPBxwAAAAAAAAAAAAAAAAAAAAF-9iKSG8ZDTqbjwKqtlAPdAgMAAAFrtcQRigBPGgC7Xy5ofIm9nbgeXjtpcbxbNO6zrzZZDsIWW5rXMqopxg\",\"expires_in\":5183999}";
        Gson g = new Gson();
        TokenBean token = g.fromJson(response, TokenBean.class);
        System.out.println(token);
    }

    public static void token(){

        String server = "http://a1.easemob.com";
        String uri = "/1103190701090583/imm/token";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders heads = new HttpHeaders();

        heads.set("Content-Type", "application/json");
        heads.set("Accept", "application/json");
        String json = "{\"grant_type\":\"client_credentials\", \"client_id\":\"YXA6fvYikhvGQ06m48CqrZQD3Q\", \"client_secret\":\"YXA6xbWTXE1FKQBxbsscomgK8DNibns\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(json, heads);

        ResponseEntity<String> responseEntity = restTemplate.exchange(server + uri, HttpMethod.POST, requestEntity, String.class);

        Gson g = new Gson();
        TokenBean token =  g.fromJson(responseEntity.getBody(), TokenBean.class);

        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getHeaders());
        System.out.println(responseEntity.getBody());

    }

}