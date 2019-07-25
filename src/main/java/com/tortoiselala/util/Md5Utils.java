package com.tortoiselala.util;

import com.tortoiselala.exception.ServerInnerErrorException;
import io.jsonwebtoken.io.Encoders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

/**
 * @author tortoiselala
 */
public class Md5Utils {

    private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 密码md5加密随机生成salt，最多32
     */
    static final int SALT_LENGTH = 20;

    /**
     * MD实例构建
     * @return
     */
    private static MessageDigest getMd5Instance(){
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            Logger logger = LoggerFactory.getLogger(Md5Utils.class);
            logger.error(e.getMessage());
            throw new ServerInnerErrorException(e.getMessage());
        }
    }

    /**
     * 32位MD5加密算法
     * @param s
     * @return
     */
    public static String convert32(String s) {
        MessageDigest md = getMd5Instance();
        int k = 0;


        byte[] bytes = s.getBytes();
        md.update(bytes);
        bytes = md.digest();

        int j = bytes.length;
        char[] chars = new char[j * 2];
        for (byte b : bytes) {
            chars[k++] = HEX_CHARS[b >>> 4 & 0xf];
            chars[k++] = HEX_CHARS[b & 0xf];
        }
        return new String(chars);
    }

    /**
     * 16位MD5加密算法
     * @param s
     * @return
     */
    public static String convert16(String s) {
        MessageDigest md = getMd5Instance();
        int k = 0;

        byte[] bytes = s.getBytes();
        md.update(bytes);
        bytes = md.digest();
        int j = bytes.length;
        char[] chars = new char[j * 2];
        for (byte b : bytes) {
            chars[k++] = HEX_CHARS[b >>> 4 & 0x0f];
            chars[k++] = HEX_CHARS[b & 0x0f];
        }
        return new String(chars).substring(8, 24);
    }

    public static String randomSalt(){
        final Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        salt = Arrays.copyOf(salt, SALT_LENGTH);
        return Encoders.BASE64.encode(salt);
    }

    public static String passwordConvert(String password, String salt){
        return convert16(password + salt);
    }
}