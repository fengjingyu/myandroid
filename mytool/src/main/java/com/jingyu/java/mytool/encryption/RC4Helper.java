package com.jingyu.java.mytool.encryption;


import com.jingyu.java.mytool.util.StringUtil;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by xilinch on 2015/5/28.
 */
public class RC4Helper {

    public static String encryByRC4(String keyStr, String data){
        if(StringUtil.isBlank(keyStr) || StringUtil.isBlank(data)){
            return "";
        }
        try {
            byte[] keyByte = keyStr.getBytes("UTF-8");
            Key key = new SecretKeySpec(keyByte,"RC4");

            Cipher cipher1 = Cipher.getInstance("RC4");
            cipher1.init(Cipher.ENCRYPT_MODE, key);

            byte[] decryStr = cipher1.doFinal(data.getBytes("UTF-8"));
            String str = Base64.getEncoder().encodeToString(decryStr);
            return str;
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }

    }
}
