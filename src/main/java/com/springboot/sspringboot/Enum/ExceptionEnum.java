package com.springboot.sspringboot.Enum;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
    NOT_EXIT(10,"不存在该商品"),
    UNDER_STOCK(20,"库存不足"),
    ORDER_NOT_EXIT(30,"该订单不存在"),
    FINISHED(40,"该订单已经结束"),
    ORDER_DETAIL_NOT_EXIT(50,"订单详情不存在"),
    ORDER_ERROR(60,"订单错状态错误"),
    PARAM_ERROR(70,"参数错误"),
    CART_NULL(80,"购物车为空"),
    IDENTITY_ERROR(90,"用户身份错误");

    private  final  Integer code;
    private  final  String msg;


    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
