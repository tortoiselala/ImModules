package com.tortoiselala.service.im.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author tortoiselala
 */
public class RestClient {
    private String server = "http://a1.easemob.com";
    private RestTemplate rest;
    private HttpHeaders headers;

    private Logger logger = LoggerFactory.getLogger(RestClient.class);

    public RestClient() {
        this.rest = new RestTemplate();
        this.rest.setErrorHandler(new CustomRestClientResponseHanlder());
        this.headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
    }

    public ResponseEntity<String> post(String uri, String body){
        return post(uri, body, null);
    }

    public ResponseEntity<String> post(String uri, String body, HttpHeaders headers) {
        HttpEntity<String> request = new HttpEntity<>(body, headers == null ? this.headers : headers);
        ResponseEntity<String> response = null;


        response = rest.exchange(server + uri, HttpMethod.POST, request, String.class);



        HttpStatus status = response.getStatusCode();

        if(status.is4xxClientError() || status.is5xxServerError()){
            logger.warn("Server response : " + response);
        }

        logger.debug("post response" + response);
        return response;


    }

    public ResponseEntity<String> get(String uri, HttpHeaders headers){
        return get(uri, "", headers);
    }

    public ResponseEntity<String> get(String uri, String body, HttpHeaders headers){
        HttpEntity<String> request = new HttpEntity<>(body, headers == null ? this.headers : headers);
        ResponseEntity<String> response;
        response = rest.exchange(server + uri, HttpMethod.GET, request, String.class);

        HttpStatus status = response.getStatusCode();

        if(status.is4xxClientError() || status.is5xxServerError()){
            logger.warn("Server response : " + response);
        }

        logger.debug("Post response" + response);
        return response;
    }

    public ResponseEntity<String> put(String uri, HttpHeaders headers){
        return put(uri, "", headers);
    }

    public ResponseEntity<String> put(String uri, String body, HttpHeaders headers){
        HttpEntity<String> request = new HttpEntity<>(body, headers == null ? this.headers : headers);
        ResponseEntity<String> response;
        response = rest.exchange(server + uri, HttpMethod.PUT, request, String.class);

        HttpStatus status = response.getStatusCode();

        if(status.is4xxClientError() || status.is5xxServerError()){
            logger.warn("Server response : " + response);
        }

        logger.debug("Post response" + response);
        return response;
    }

    public ResponseEntity<String> delete(String uri, HttpHeaders headers){
        return delete(uri, "", headers);
    }

    public ResponseEntity<String> delete(String uri, String body, HttpHeaders headers){
        HttpEntity<String> request = new HttpEntity<>(body, headers == null ? this.headers : headers);
        ResponseEntity<String> response;
        response = rest.exchange(server + uri, HttpMethod.DELETE, request, String.class);

        HttpStatus status = response.getStatusCode();

        if(status.is4xxClientError() || status.is5xxServerError()){
            logger.warn("Server response : " + response);
        }

        logger.debug("Post response" + response);
        return response;
    }
}
