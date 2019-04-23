package com.springboot.sspringboot.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = -4881199013680533758L;
    @Id
    private String detailId;

    private  String  orderId;

    private String productId;

    private String  productName;

    private BigDecimal productPrice;

    private Integer productQuantity;

    private String productIcon;
}
