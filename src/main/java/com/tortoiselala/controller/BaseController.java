package com.tortoiselala.controller;

import com.tortoiselala.bean.RsaKeyBean;
import com.tortoiselala.bean.UserBean;
import com.tortoiselala.exception.ParameterExtractException;
import com.tortoiselala.util.RsaUtils;
import org.apache.commons.validator.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.regex.Pattern;

/**
 * @author tortoiselala
 */
public class BaseController {
    /**
     * 请求头中用户名的key
     */
    private static final String KEY_UID = "uid";

    /**
     * 请求头中密码的key
     */
    private static final String KEY_PASSWORD = "password";

    /**
     * 请求头中email的key
     */
    private static final String KEY_EMAIL = "email";

    /**
     * 系统初始化时生成RSA秘钥对
     */
    private RsaKeyBean key = new RsaUtils().generateKeyPair();

    static final String TOKEN_IDENTIFIER = "Token from imm server";

    static final String TOKEN_ISSUER = "imm Authorization server";

    static final String TOKEN_AUDIENCE = "user";

    private Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 获取公钥
     * @return public key
     */
    PublicKey getRsaPublicKey(){
        PublicKey publicKey;
        if(key == null || (publicKey = key.getPublicKey()) == null){
            logger.error("RSA秘钥生成失败");
            return null;
        }
        return publicKey;
    }

    /**
     * 获取私钥
     * @return private key
     */
    PrivateKey getRsaPrivateKey(){
        PrivateKey privateKey;
        if(key == null || (privateKey = key.getPrivateKey()) == null){
            logger.error("RSA秘钥生成失败");
            return null;
        }
        return privateKey;
    }

    /**
     * 获取请求参数
     * @param request request
     * @param key parameter key
     * @return paraneter
     */
    private String getRequestParameters(HttpServletRequest request, String key){
       return request.getParameter(key);
    }

    /**
     * 获取请求头中的账号
     * @param request
     * @return
     */
    final String getUidFromRequest(HttpServletRequest request) throws ParameterExtractException {

        String uid = getRequestParameters(request, KEY_UID);

        if(uid == null){
            throw new ParameterExtractException("request no uid");
        }
        String pattern = "[a-zA-Z0-9]*";
        boolean isMatch = Pattern.matches(pattern, uid);

        if(!isMatch){
            throw new ParameterExtractException("Illegal uid");
        }

        uid = uid.toLowerCase();
        return uid;
    }

    /**
     * 获取请求头中的密码，并解密
     * @param request request
     * @return 解密后的密码
     */
    final String getPasswordFromRequest(HttpServletRequest request) throws ParameterExtractException {
        String password = getRequestParameters(request, KEY_PASSWORD);
        PrivateKey privateKey = getRsaPrivateKey();
        if(password == null){
            throw new ParameterExtractException("request no password");
        }
        byte[] de = RsaUtils.decrypt(privateKey, password.getBytes());
        if(de == null){
            throw new ParameterExtractException("password is not encrypted");
        }
        return new String(de);
    }

    /**
     * 获取请求头中的邮箱并做合法性检查
     * @param request
     * @return email
     */
    final String getEmailFromRequest(HttpServletRequest request) throws ParameterExtractException {
        String email = getRequestParameters(request, KEY_EMAIL);
        if(email == null){
            throw new ParameterExtractException("request no email");
        }
        EmailValidator ev = EmailValidator.getInstance();
        if(!ev.isValid(email)){
            throw new ParameterExtractException("email is illegal");
        }
        return email;
    }

    /**
     * 从请求中抽取用户名和解密后的密码信息
     * @param request
     * @return
     * @throws ParameterExtractException
     */
    UserBean userAuthorityInformationExtract(HttpServletRequest request) throws ParameterExtractException {
        String uid = getUidFromRequest(request);
        String password = getPasswordFromRequest(request);
        return new UserBean(uid, password);
    }

    /**
     * 从请求中抽取用户名和解密后的密码信息
     * @param request
     * @return
     * @throws ParameterExtractException
     */
    UserBean userRegisterInformationExtract(HttpServletRequest request) throws ParameterExtractException {
        String uid = getUidFromRequest(request);
        String password = getPasswordFromRequest(request);
        String email = getEmailFromRequest(request);

        UserBean user = new UserBean();
        user.setUid(uid);
        user.setPassword(password);
        user.setEmail(email);

        return user;
    }


}
