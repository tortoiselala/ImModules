package com.tortoiselala.controller;

import com.tortoiselala.bean.ProcessResultBean;
import com.tortoiselala.bean.ResponseBean;
import com.tortoiselala.bean.UserBean;
import com.tortoiselala.exception.ParameterExtractException;
import com.tortoiselala.exception.RegistrationException;
import com.tortoiselala.service.im.ImUserService;
import com.tortoiselala.service.local.UserService;
import com.tortoiselala.util.JwtUtils;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tortoiselala
 */
@Controller
@RequestMapping(value = "/account")
public class AccountController extends BaseController{

    @Autowired
    UserService userService;

    @Autowired
    ImUserService imUserService;

    /**
     * 用户权限认证与证书颁布接口
     * 方法：POST
     * 返回：JSON
     * @param request
     * @return
     */
    @RequestMapping(
            value = "/token",
            method = {RequestMethod.POST},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ResponseBody
    public ResponseBean<String> token(HttpServletRequest request){
        ResponseBean<String> response = new ResponseBean<>();
        UserBean user;
        try {
            user = userAuthorityInformationExtract(request);
        } catch (ParameterExtractException e) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return response;
        }

        boolean illegal = userService.authentication(user);

        if(!illegal){
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("login failed, username or password is incorrect");
            return response;
        }
        //token签名算法使用RS256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;
        //生成token的时间
        long now = System.currentTimeMillis();
        //
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        // payload的私有声明
        Map<String,Object> claims = new HashMap<>();
        // payload填充
        JwtBuilder builder =
                Jwts.builder()
                        // 自定义属性
                        .setClaims(claims)
                        // 令牌标识 jti
                        .setId(TOKEN_IDENTIFIER)
                        .setHeader(headers)
                        // 令牌颁发时间
                        .setIssuedAt(new Date(now))
                        // 令牌所属主体，sub
                        .setSubject(user.getUid())
                        // 令牌生效时间，nbf
                        .setNotBefore(new Date(now))
                        // 颁发者
                        .setIssuer(TOKEN_ISSUER)
                        // 作用域
                        .setAudience(TOKEN_AUDIENCE)
                        // 过期时间, exp
                        .setExpiration(new Date(now + JwtUtils.EXPIRATION))
                        // 签名
                        .signWith(getRsaPrivateKey(), signatureAlgorithm);
        String jws = builder.compact();
        response.setCode(HttpStatus.OK.value());
        response.setData(jws);
        return response;
    }


    @RequestMapping(
            value = "/register",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ResponseBody
    public ResponseBean<UserBean> register(HttpServletRequest request){
        ResponseBean<UserBean> response = new ResponseBean<>();
        UserBean user;
        try {
            user = userRegisterInformationExtract(request);

            boolean localReg = userService.register(user);
            if(!localReg){
                throw new RegistrationException("Local database registration failed");
            }

            ProcessResultBean imReg= imUserService.register(user.getUid(), user.getPassword());
            if(!imReg.isSuccess()){
                throw new RegistrationException("IM server registration failed");
            }
            response.setCode(HttpStatus.OK.value());
            response.setData(user);
        } catch (ParameterExtractException | RegistrationException e) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return response;
        }
        return response;
    }
}
