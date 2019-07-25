package com.tortoiselala.util;

import com.tortoiselala.bean.RsaKeyBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author tortoiselala
 */
public class RsaUtils {
    private Logger logger = LoggerFactory.getLogger(RsaUtils.class);
    /**
     * 加密算法
     */
    public static String ENCRYPTION_ALGORITHM = "RSA";

    /**
     * 秘钥长度
     */
    public static int ENCRYPTION_KEY_LENGTH = 2048;

    /**
     * 生成秘钥对
     * @return
     */
    public RsaKeyBean generateKeyPair() {
        KeyPairGenerator gen = null;
        try {
            gen = KeyPairGenerator.getInstance(ENCRYPTION_ALGORITHM);
            gen.initialize(ENCRYPTION_KEY_LENGTH);
        } catch (NoSuchAlgorithmException e) {
            logger.error("加密方式错误  " + e.getMessage());
            return null;
        }
        RsaKeyBean bean = new RsaKeyBean(gen.generateKeyPair());
        System.out.println("encrypt 1546191727 : " + Base64Utils.encodeToString(RsaUtils.encrypt(bean.getPublicKey(), "1546191727".getBytes())));
        return bean;
    }

    /**
     * The method that will decrypt an array of bytes.
     *
     * @param key
     * 		The {@link PrivateKey} used to decrypt the data.
     *
     * @param data
     * 		The encrypted byte array.
     *
     * @return The decrypted data, otherwise {@code null} if decryption could not be performed.
     */
    public static byte[] encrypt(PublicKey key, byte[] data) {
        try {

            final Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);

            cipher.init(Cipher.ENCRYPT_MODE, key);

            return cipher.doFinal(data);

        } catch (Exception ex) {

        }
        return null;
    }
    /**
     * The method that will decrypt an array of bytes.
     *
     * @param key
     * 		The {@link PrivateKey} used to decrypt the data.
     *
     * @param encryptedData
     * 		The encrypted byte array.
     *
     * @return The decrypted data, otherwise {@code null} if decryption could not be performed.
     */
    public static byte[] decrypt(PrivateKey key, byte[] encryptedData) {

        try {

            final Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);

            cipher.init(Cipher.DECRYPT_MODE, key);

            return cipher.doFinal(encryptedData);

        } catch (Exception ex) {

        }

        return null;

    }
}
