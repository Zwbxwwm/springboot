package com.springboot.sspringboot.Enum;

public enum OrderStatus implements codeEnum {
    NEW_ORDER(0,"新订单"),
    FINISH_ORDER(1,"已完结订单"),
    CANCEL_ORDER(2,"已取消订单"),;

    //订单支付状态

    private final int code;
    private final String msg;

    OrderStatus(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
