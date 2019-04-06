package com.springboot.sspringboot.exception;

import com.springboot.sspringboot.Enum.ExceptionEnum;
import com.springboot.sspringboot.Enum.ResponseCode;
import lombok.Getter;

@Getter
public class sellException extends RuntimeException {
    private Integer code;

    public sellException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
    }

    public sellException(ResponseCode responseCode) {
        super(responseCode.getMsg());
        this.code = responseCode.getCode();
    }

    public sellException(Integer code,String msg) {
        super(msg);
        this.code = code;
    }
}
