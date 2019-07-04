package com.tortoiselala.util;

import com.tortoiselala.bean.RsaKeyBean;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author tortoiselala
 */
public class RsaUtilsTest {

    @Test
    public void test() {
        RsaUtils rsaUtils = new RsaUtils();
        RsaKeyBean key = rsaUtils.generateKeyPair();
        char[] or = "abcdef".toCharArray();
        System.out.println("-- start RsaUtils test");

        System.out.println("-- public key :" + key.getPublicKey());

        System.out.println("-- private key :" + key.getPrivateKey());

        byte[] en = RsaUtils.encrypt(key.getPublicKey(), new String(or).getBytes());
        byte[] de = RsaUtils.decrypt(key.getPrivateKey(), en);
        System.out.println("-- 'abcdef' after en" + Arrays.toString(en));

        System.out.println("-- 'abcdef' after de" + Arrays.toString(de));

        for(int i = 0; i < or.length; ++i){
            if(or[i] == de[i]){
                System.out.println("equals, or[i]:" + or[i]);
            }else{
                System.out.println("not equals, en[i]:" + or[i] + "de[i]:" + (char)(de[i]));
            }
        }

        System.out.println("-- end RsaUtils test");
    }
}