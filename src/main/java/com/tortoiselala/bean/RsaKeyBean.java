package com.tortoiselala.bean;

import lombok.Getter;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author tortoiselala
 */

@Getter
public class RsaKeyBean {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public RsaKeyBean(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public RsaKeyBean(KeyPair key) {
        this.privateKey = key.getPrivate();
        this.publicKey = key.getPublic();
    }
}
