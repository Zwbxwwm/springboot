package com.springboot.sspringboot.common;


import com.springboot.sspringboot.Enum.ResponseCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class ServerResponse<T> implements Serializable {
    private static final long serialVersionUID = -829390125875134984L;

    private int code;
    private String msg;
    private T data;

    public ServerResponse(String msg) {
        this.msg = msg;
    }

    public ServerResponse(T data) {
        this.data = data;
    }

    public ServerResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public ServerResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static <T> ServerResponse<T> Success() {
        return new ServerResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), null);
    }

    public static <T> ServerResponse<T> Success(T data) {
        return new ServerResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), data);
    }

    public static <T> ServerResponse<T> Success(int code, String msg) {
        return new ServerResponse(code, msg);
    }

    public static <T> ServerResponse<T> Success(int code, String msg, T data) {
        return new ServerResponse(code, msg, data);
    }

    public static <T> ServerResponse<T> Error(T data) {
        return new ServerResponse(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMsg(), data);
    }

    public static <T> ServerResponse<T> Error(int code, String msg) {
        return new ServerResponse(code, msg);
    }

    public static <T> ServerResponse<T> Error(int code, String msg, T data) {
        return new ServerResponse(code, msg, data);
    }


}
