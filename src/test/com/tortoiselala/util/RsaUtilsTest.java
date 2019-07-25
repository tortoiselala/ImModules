package com.tortoiselala.util;

import com.tortoiselala.bean.RsaKeyBean;
import org.junit.Test;
import org.springframework.util.Base64Utils;


/**
 * @author tortoiselala
 */
public class RsaUtilsTest {

    @Test
    public void test() {
        RsaUtils rsaUtils = new RsaUtils();
        RsaKeyBean key = rsaUtils.generateKeyPair();

        String password = "1546191727";
        byte[] passwordAfterEncode = RsaUtils.encrypt(key.getPublicKey(), password.getBytes());

        assert passwordAfterEncode != null;
        String passwordEncodeBase64UrlSafeString = Base64Utils.encodeToString(passwordAfterEncode);
        System.out.println("after base64 encode :" + passwordEncodeBase64UrlSafeString);
        byte[] passwordDecodeBase64UrlSafeString = Base64Utils.decodeFromString(passwordEncodeBase64UrlSafeString);

        byte[] passwordAfterDecode = RsaUtils.decrypt(key.getPrivateKey(), passwordDecodeBase64UrlSafeString);

        assert passwordAfterDecode != null;
        System.out.println(new String(passwordAfterDecode));
    }
}