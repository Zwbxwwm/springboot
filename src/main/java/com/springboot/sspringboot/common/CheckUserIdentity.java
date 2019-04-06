package com.springboot.sspringboot.common;

import com.springboot.sspringboot.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CheckUserIdentity {
    public static  boolean check(String openid, OrderDTO orderDTO){
        if(openid.equalsIgnoreCase(orderDTO.getBuyerOpenid())) {
            return true;
        }else{
            log.info("【校验身份】:身份匹配错误，o-openid={},n-openid",openid,orderDTO.getBuyerOpenid());
            return false;
        }
    }
}
