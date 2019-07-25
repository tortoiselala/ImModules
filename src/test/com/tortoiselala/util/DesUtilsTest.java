package com.tortoiselala.util;

import static org.junit.Assert.*;

/**
 * @author tortoiselala
 */
public class DesUtilsTest {
    public static void main(String[] args) {
        String[] or = {"root", "remote1546191727",
                "YXA6fvYikhvGQ06m48CqrZQD3Q",
                "YXA6xbWTXE1FKQBxbsscomgK8DNibns"};
        for (String s : or) {
            System.out.println(s + " : " + DesUtils.encode(s) + " : " + DesUtils.decode(DesUtils.encode(s)));
        }
    }
}