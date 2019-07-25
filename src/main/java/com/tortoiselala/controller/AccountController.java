package com.tortoiselala.controller;

import com.tortoiselala.bean.ProcessResultBean;
import com.tortoiselala.bean.ResponseBean;
import com.tortoiselala.bean.UserBean;
import com.tortoiselala.exception.RegistrationException;
import com.tortoiselala.service.im.ImUserService;
import com.tortoiselala.service.local.UserService;
import com.tortoiselala.util.JwtUtils;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author tortoiselala
 */
@Controller
public class AccountController extends BaseController{

    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    UserService userService;

    @Autowired
    ImUserService imUserService;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

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
    public ResponseBean token(HttpServletRequest request){

            String type = getRequestParameters(request, KEY_TYPE);
            // /token or /token?type=get
            if(type == null || type.equals(KEY_TYPE_GET)){
                return getToken(request);
            }else if(type.equals(KEY_TYPE_VALIDATE)){
                // /token?type=validate
                return validateToken(request);
            }
        return null;
    }


    @RequestMapping(
            value = "/register",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ResponseBody
    public ResponseBean<UserBean> register(HttpServletRequest request){
        ResponseBean<UserBean> response = new ResponseBean<>();
        String uid = getRequestParameters(request, KEY_UID);
        String password = getRequestParameters(request, KEY_PASSWORD);
        String email = getRequestParameters(request, KEY_EMAIL);

        UserBean user = new UserBean(uid, password);
        user.setEmail(email);

        try {
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
        } catch (RegistrationException e) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return response;
        }
        return response;
    }

    private ResponseBean<String> getToken(HttpServletRequest request){
        ResponseBean<String> response = new ResponseBean<>();
        String uid = getRequestParameters(request, KEY_UID);
        String password = getRequestParameters(request, KEY_PASSWORD);
        UserBean user = new UserBean(uid, password);

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
        storeToRedis("token:" + uid + ":pc", jws);
        response.setCode(HttpStatus.OK.value());
        response.setData(jws);
        return response;
    }

    private ResponseBean<String> validateToken(HttpServletRequest request){
        String token = getRequestParameters(request, KEY_TOKEN);
        PublicKey key = getRsaPublicKey();
        try{
            Claims declaims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token).getBody();
        }catch(Throwable e){
            logger.error(e.getMessage());
            return new ResponseBean<>(HttpStatus.BAD_REQUEST.value(), "The token content has been tampered");
        }
        return new ResponseBean<>(HttpStatus.OK.value(), "ok");
    }

    private void storeToRedis(String key, String value){
        redisTemplate.opsForValue().set(key, value, JwtUtils.EXPIRATION, TimeUnit.MINUTES);
    }

}
