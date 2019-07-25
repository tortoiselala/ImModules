package com.tortoiselala.controller;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.tortoiselala.bean.RsaKeyBean;
import com.tortoiselala.bean.UserBean;
import com.tortoiselala.exception.ParameterExtractException;
import com.tortoiselala.util.RsaUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.validator.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

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
    public static final String KEY_UID = "uid";

    /**
     * 请求头中密码的key
     */
    public static final String KEY_PASSWORD = "password";

    /**
     * 请求头中email的key
     */
    public static final String KEY_EMAIL = "email";

    /**
     * 请求中的email的key
     */
    public static final String KEY_TOKEN = "token";

    public static final String KEY_TYPE = "type";

    public static final String KEY_TYPE_GET = "type_get";

    public static final String KEY_TYPE_VALIDATE = "validate";

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
    protected String getRequestParameters(HttpServletRequest request, String key){
       return request.getParameter(key);
    }

    /**
     * 账号
     * @param request
     * @return
     */
    final String checkUid(HttpServletRequest request) throws ParameterExtractException {

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
     * 使用私钥解密密码
     * @param password password
     * @return 解密后的密码
     */
    final String decryptPassword(String password) throws ParameterExtractException {
        PrivateKey privateKey = getRsaPrivateKey();
        if(password == null){
            throw new ParameterExtractException("request has no password");
        }
        byte[] d = Base64Utils.decodeFromString(password);
        byte[] de = RsaUtils.decrypt(privateKey, d);
        if(de == null){
            throw new ParameterExtractException("password is not encrypted");
        }
        return new String(de);

    }

    /**
     * 对邮箱做合法性检查
     * @param email
     * @return email
     */
    final String checkEmail(String email) throws ParameterExtractException {
        if(email == null){
            throw new ParameterExtractException("request no email");
        }
        EmailValidator ev = EmailValidator.getInstance();
        if(!ev.isValid(email)){
            throw new ParameterExtractException("email is illegal");
        }
        return email;
    }
}
