package com.tortoiselala.util;

import org.junit.Test;


/**
 * @author tortoiselala
 */
public class Md5UtilsTest {

    @Test
    public void encode() {

        System.out.println(Md5Utils.convert32("1546191727"));

    }

    @Test
    public void randomSalt() {
        System.out.println(Md5Utils.randomSalt());
    }
}