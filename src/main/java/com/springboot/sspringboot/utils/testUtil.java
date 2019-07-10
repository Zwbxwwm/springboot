package com.springboot.sspringboot.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class testUtil {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Map<String,String> param =new TreeMap<>((k1,k2)->{
            return k1.compareTo(k2);
        });
        param.put("client_secret","560c04df96cf65286f4b59f3307550f0");
        param.put("client_id","141b476b6c59a928cd7297ac15d0b74f_test");
        param.put("grant_type","client_credentials");
        param.put("phone","00016027412");
        param.forEach((k,v)->{
            System.out.println(k+":"+v);
        });
    }

}
