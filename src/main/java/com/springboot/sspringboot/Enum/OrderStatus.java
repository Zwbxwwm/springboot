package com.springboot.sspringboot.Enum;

public enum OrderStatus {
    NEW_ORDER(0,"新订单"),
    FINISH_ORDER(1,"已完结订单"),
    CANCEL_ORDER(2,"已取消订单"),

    //订单支付状态
    UN_PAY(0,"未支付"),
    HAVE_PAY(1,"已支付");

    private final int code;
    private final String msg;

    OrderStatus(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
