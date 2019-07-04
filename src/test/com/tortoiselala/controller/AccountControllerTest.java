package com.tortoiselala.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tortoiselala
 */
public class AccountControllerTest {

    @Test
    public void token() throws InterruptedException {
        String username = "username";
//        String password = "password";
        BaseController bc = new BaseController();
        PublicKey publicKey = bc.getRsaPublicKey();
        PrivateKey privateKey = bc.getRsaPrivateKey();

        //token签名算法使用RS256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;
        //生成token的时间
        long now = System.currentTimeMillis();
        //
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        // payload的私有声明
        Map<String,Object> claims = new HashMap<>();
        //payload填充
        JwtBuilder builder = Jwts.builder()
                        // 自定义属性
                        .setClaims(claims)
                        // 令牌标识 jti
                        .setId("random")
                        .setHeader(headers)
                        // 令牌颁发时间
                        .setIssuedAt(new Date(now))
                        // 令牌所属主体，sub
                        .setSubject(username)

                .setExpiration(new Date(System.currentTimeMillis()))
                        // 令牌生效时间，nbf
                        .setNotBefore(new Date(now))
                        .setIssuer("imm Authorization server")
                        .setAudience("user")
                        // 签名
                        .signWith(privateKey, signatureAlgorithm);
        String jws = builder.compact();
        System.out.println(
                "public key:" + publicKey.getFormat()
                + '\n' + "private key:" + privateKey.getFormat()
                + '\n' + "jws :" + jws
        );

        Thread.sleep(10000);

        Claims declaims = Jwts.parser()  //得到DefaultJwtParser
                .setSigningKey(publicKey)         //设置签名的秘钥
                .parseClaimsJws(jws).getBody();//设置需要解析的jwt
        System.out.println(
                " id :" + declaims.getId()
                + "\n getIssuer :" + declaims.getIssuer()
                + "\n getSubject :" + declaims.getSubject()
                + "\n getNotBefore :" + declaims.getNotBefore()
                + "\n getExpiration :" + declaims.getExpiration()
                + "\n getAudience :" + declaims.getAudience()
        );
        declaims.getId();
    }
}