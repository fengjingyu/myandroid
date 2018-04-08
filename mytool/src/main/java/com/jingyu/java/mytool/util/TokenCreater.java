package com.jingyu.java.mytool.util;

/**
 * Created by jingyu on 2018/4/8.
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import sun.misc.BASE64Encoder;

public class TokenCreater {

    private TokenCreater() {
    }

    private static final TokenCreater instance = new TokenCreater();

    public static TokenCreater getInstance() {
        return instance;
    }

    public String makeToken(String id) {
        String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "" + id;
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte md5[] = md.digest(token.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(md5);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
