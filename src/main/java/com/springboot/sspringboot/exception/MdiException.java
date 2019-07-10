package com.springboot.sspringboot.exception;

import com.springboot.sspringboot.Enum.ExceptionEnum;
import com.springboot.sspringboot.Enum.ResponseCode;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class MdiException extends RuntimeException {
    private String code;
    private String msg;

    public MdiException(String code, String msg, IOException e) {
        this.msg = msg;
        this.code = code;
    }
    public MdiException(String code, String msg, KeyManagementException e){
        this.msg = msg;
        this.code = code;
    }

    public MdiException(String code, String msg, NoSuchAlgorithmException e) {
        this.code = code;
        this.msg = msg;
    }

    public MdiException(String code, String msg, KeyStoreException e) {
        this.code = code;
        this.msg = msg;
    }
}
