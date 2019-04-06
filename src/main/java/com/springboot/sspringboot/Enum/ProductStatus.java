package com.springboot.sspringboot.Enum;

public enum ProductStatus {
    ON_SALE(0,"此产品上架状态"),
    OUT_SALE(1,"此产品已下架");
    private final  int code;
    private final  String msg;

    ProductStatus(int code, String msg){
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
