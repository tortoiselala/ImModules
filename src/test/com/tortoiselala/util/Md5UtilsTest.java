package com.tortoiselala.util;

import org.junit.Test;

import java.sql.SQLOutput;


/**
 * @author tortoiselala
 */
public class Md5UtilsTest {

    @Test
    public void encode() {

        String salt = Md5Utils.randomSalt();
        String password = "1546191727";
        String convert = Md5Utils.passwordConvert(password, salt);
        System.out.println("salt:" + salt);
        System.out.println("password:" + password);
        System.out.println("convert:" + convert);

    }

    @Test
    public void randomSalt() {
        System.out.println(Md5Utils.randomSalt());
    }
}