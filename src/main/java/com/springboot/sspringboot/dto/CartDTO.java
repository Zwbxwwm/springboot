package com.springboot.sspringboot.dto;


import lombok.Data;

@Data
public class CartDTO {
    /**购物车商品id*/
    private  String productId;

    /**该商品数量*/
    private Integer productQuantity;

    public CartDTO() {
    }

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
