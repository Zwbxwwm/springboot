package com.springboot.sspringboot.Enum;

public enum ResponseCode {
    SUCCESS(0,"请求成功"),
    ERROR(1,"请求失败"),
    ERROR_VALID(70,"传入参数有误");
    private final int code;
    private final String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
