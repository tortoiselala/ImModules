package com.tortoiselala.service.im.impl;

import com.tortoiselala.service.im.Account;
import org.springframework.stereotype.Service;

/**
 * @author tortoiselala
 */
@Service
public class AccountImpl implements Account {

    @Override
    public boolean createAccount(String uid, String password) {
        return false;
    }
}
