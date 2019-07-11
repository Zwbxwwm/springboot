package com.springboot.sspringboot.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
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


    public static String getSignature(HashMap<String, String> params, String secret) throws IOException {
        params.put("sign_key",secret);
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();
        params.forEach((k,v) -> System.out.println(k+": "+v));
        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            basestring.append(param.getKey()).append("=").append(param.getValue()).append("&");
        }
        if(basestring.length()>1)
            basestring.deleteCharAt(basestring.length()-1);
        // 使用MD5对待签名串求签
        byte[] bytes = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
        } catch (GeneralSecurityException ex) {
            throw new IOException(ex);
        }

        // 将MD5输出的二进制结果转换为小写的十六进制
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }


    /**
     * params需要为LinkedHashMap(有序map) ，值为调用接口的参数，除sign外
     **/
    public static String genSign(Map<String, String> params, String signKey) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //将sign_key添加进去
        params.put("sign_key", signKey);

        Map<String,String> param =new TreeMap<>((k1,k2)-> k1.compareTo(k2));

        for(String key: params.keySet()){
            param.put(key,params.get(key));
        }

        //用&符号连接每一个键值对
        String str = "";
        for (Map.Entry<String, String> entry : param.entrySet()) {
            if ("" == str) {
                str += entry.getKey() + "=" + entry.getValue().trim();
            } else {
                str += "&" + entry.getKey() + "=" + entry.getValue().trim();
            }
        }
        //最好记录这个加密前的字符串，防止签名出错时进行排查，
        //eg:access_token=test_access_token&client_id=test_client_id&timestamp=1559736242507&sign_key=test_sign_key
        //System.out.println(str);

        System.out.println(str);
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = md.digest(str.getBytes("UTF-8"));
        StringBuilder sign = new StringBuilder();
        for(int i = 0; i < bytes.length; i++){
            String hex = Integer.toHexString(bytes[i] & 0xff);
            if(hex.length() == 1)
                sign.append("0");
            sign.append(hex);
        }
        return sign.toString();
    }

    public static String authorizeSign() throws NoSuchAlgorithmException, IOException {
        HashMap<String,String> param = new HashMap<>();
        param.put("client_secret","560c04df96cf65286f4b59f3307550f0");
        param.put("client_id","141b476b6c59a928cd7297ac15d0b74f_test");
        param.put("grant_type","client_credentials");
        param.put("phone","00016027412");
        String  timestamp=Integer.parseInt(String.valueOf(System.currentTimeMillis()/1000))+"";
        System.out.println(timestamp);
        param.put("timestamp",timestamp);
        String singKey = "a372D45240cc07E82e7a";

        return getSignature(param,singKey);
    }

    public static  String orderDetailSign() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Map<String,String> param = new LinkedHashMap();
        param.put("client_id","141b476b6c59a928cd7297ac15d0b74f_test");
        param.put("access_token","f2feb98d3a5d00e92daea36a1462ead2a365498f");
        String  timestamp=Long.toString(new Date().getTime());
        param.put("timestamp",timestamp);
        param.put("company_id","1125903803487621");
        param.put("order_id","");

        String singKey = "a372D45240cc07E82e7a";
        return genSign(param,singKey);
    }

    public static void main(String[] args) throws Exception {
        //生成data_encode
        String key = "a372D452";
        Map data = new HashMap<>();
        data.put("client_id","141b476b6c59a928cd7297ac15d0b74f_test");
        data.put("client_secret","560c04df96cf65286f4b59f3307550f0");
        data.put("master_phone","00016027412");
        data.put("passenger_phone","11116027412");
        data.put("auth_type","1");
//        data.put("city_id",36);
//        data.put("to_city_id",36);
//        data.put("require_level_list","900");

        String encryptData = encrypt(JSON.toJSONString(data), key);
        System.out.println("data_encode: " + encryptData);
        System.out.println("access_token_sign: "+ authorizeSign());

    }
}
