package com.springboot.sspringboot.Enum;

public enum PayStatus implements codeEnum {
    UN_PAY(0,"未支付"),
    HAVE_PAY(1,"已支付");

    private final int code;
    private final String msg;

    PayStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
