package com.tortoiselala.util;

import com.tortoiselala.config.ImConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author tortoiselala
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-*.xml"})
public class ImConfigurationTest {

    @Autowired
    ImConfiguration imConfiguration;

    @Test
    public void test() {
        System.out.println(imConfiguration.getAppKey());
        System.out.println(imConfiguration.getClientId());
        System.out.println(imConfiguration.getClientSecret());
    }
}