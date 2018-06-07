package com.jingyu.java.mytool.encryption;


import com.jingyu.java.mytool.util.StringUtil;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

/**
 * Created by xilinch on 2015/6/1.
 */
public class RSA {

    public static final String RSA = "RSA";
    public static final String RSA1 = "RSA/ECB/PKCS1Padding";

    public static String encryByRSA(String publicKeyStr, String data){
        if(StringUtil.isBlank(publicKeyStr) || StringUtil.isBlank(data)){
            return "";
        }
        try {
//                byte[] buffer = Base64Helper.decode(publicKeyStr);
//                KeyFactory keyFactory = KeyFactory.getInstance(RSA);
//                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
//                RSAPublicKey rsaPublicKey =  (RSAPublicKey) keyFactory.generatePublic(keySpec);
                PublicKey publicKey = getPublicKey(publicKeyStr);
                Cipher cipher = Cipher.getInstance(RSA1);
                // 编码前设定编码方式及密钥
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                // 传入编码数据并返回编码结果
                byte[] result = cipher.doFinal(data.getBytes());
                String resultStr = Base64.getEncoder().encodeToString(result);
                return resultStr;

        } catch (Exception e){
            e.printStackTrace();
            return "";
        }

    }


    /**
     * 得到公钥
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.getDecoder().decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 得到私钥
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }
}
