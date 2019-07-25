package com.tortoiselala.controller;

import com.tortoiselala.bean.ResponseBean;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
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
    public ResponseBean<String> getPublicKey(){
        PublicKey key = getRsaPublicKey();
        ResponseBean<String> response = new ResponseBean<>();
        if(key == null){
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal server error");
        }else{
            response.setCode(HttpStatus.OK.value());
            response.setData(Base64Utils.encodeToString(key.getEncoded()));
        }
        return response;
    }
}
