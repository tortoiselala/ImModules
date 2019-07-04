package com.tortoiselala.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @author tortoiselala
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private String[] encryptPropertyNames = {
            "master.username", "master.password",
            "slave.username", "slave.password",
            "client_id", "client_secret"};

    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
       Logger logger = LoggerFactory.getLogger(EncryptPropertyPlaceholderConfigurer.class);

        if(isEncryptProp(propertyName)){
            logger.debug("decrypt " + propertyName);
            String de = DesUtils.decode(propertyValue);
            logger.debug("prop value : " + de);
            return de;
       }
        return propertyValue;
    }

    private boolean isEncryptProp(String name){
        for (String e : encryptPropertyNames) {
            if(e.equals(name)){
                return true;
            }
        }
        return false;
    }
}
