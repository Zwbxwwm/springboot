package com.springboot.sspringboot.utils;

import java.util.Random;

public class IdUtil {

    /**
     * 生成唯一的ID
     * 规则：根据时间生成固定长度的ID
     * @return
     */
    public static synchronized String createUniqueKey(){
        Random random =new Random();
         Integer number = random.nextInt(900000)+1000000;
         return System.currentTimeMillis()+String.valueOf(number);
    }
}
