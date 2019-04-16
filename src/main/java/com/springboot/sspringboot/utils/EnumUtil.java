package com.springboot.sspringboot.utils;

import com.springboot.sspringboot.Enum.codeEnum;

public class EnumUtil {
    public static <T extends codeEnum> T getEnumByCode(Integer code, Class<T> enumClass){
        for(T item:enumClass.getEnumConstants()){
            if(item.getCode() == code){
                return item;
            }
        }
        return null;
    }
}
