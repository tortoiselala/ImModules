package com.tortoiselala.controller;

import com.tortoiselala.bean.ResponseBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.PublicKey;

/**
 * 密钥服务
 * @author tortoiselala
 */

@Controller
@RequestMapping(value = "/key")
public class KeyController extends BaseController{


    @RequestMapping(
            value = "/getPublicKey",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ResponseBody
    public ResponseBean<byte[]> getPublicKey(){
        PublicKey key = getRsaPublicKey();
        ResponseBean<byte[]> response = new ResponseBean<>();
        if(key == null){
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal server error");
        }else{
            response.setCode(HttpStatus.OK.value());
            response.setData(key.getEncoded());
        }
        return response;
    }
}
