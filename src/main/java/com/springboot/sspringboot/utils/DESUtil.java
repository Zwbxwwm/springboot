package com.springboot.sspringboot.utils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;

public class DESUtil {
    //算法名称
    public static final String KEY_ALGORITHM = "DES";
    //算法名称/加密模式/填充方式

//DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
    public static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";
    /**
     *
     * 生成密钥key对象
     * @param keyStr 密钥字符串
     * @return 密钥对象
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws Exception
     */
    private static SecretKey keyGenerator(String keyStr) throws Exception {
        DESKeySpec desKey = new DESKeySpec(keyStr.getBytes("UTF-8"));
        //创建一个密匙工厂，然后用它把DESKeySpec转换成
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        return securekey;
    }
    /**
     * 加密数据
     * @param data 待加密数据
     * @param key 密钥
     * @return 加密后的数据
     */
    public static String encrypt(String data, String key) throws Exception {
        Key deskey = keyGenerator(key);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        // 实例化Cipher对象，它用于完成实际的加密操作
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 初始化Cipher对象，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, deskey, iv);
        byte[] results = cipher.doFinal(data.getBytes("UTF-8"));
        // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
        return Base64.encodeBase64String(results);
    }
    /**
     * 解密数据
     * @param data 待解密数据
     * @param key 密钥
     * @return 解密后的数据
     */
    public static String decrypt(String data, String key) throws Exception {
        Key deskey = keyGenerator(key);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化Cipher对象，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, deskey, iv);
        // 执行解密操作
        return new String(cipher.doFinal(Base64.decodeBase64(data)));
    }

    public static void main(String[] args) throws Exception {

        Map data = new HashMap<>();
        data.put("client_id","141b476b6c59a928cd7297ac15d0b74f_test");
        data.put("client_secret","560c04df96cf65286f4b59f3307550f0");
        data.put("master_phone","00016027412");
        data.put("passenger_phone","00016027412");
        data.put("auth_type",1);
        data.put("city_id",36);
        data.put("to_city_id",36);
        data.put("require_level_list","900");

        //a372D45240cc07E82e7a
        String source = data.toString();
        System.out.println("原文: " + source);


        String objectJson = JSON.toJSONString(data);
        Map object = JSON.parseObject(objectJson);
        System.out.println(object.equals(data));

        String key = "a372D452";
        String encryptData = encrypt(JSON.toJSONString(data), key);
        System.out.println("加密后: " + encryptData);


    }
}
