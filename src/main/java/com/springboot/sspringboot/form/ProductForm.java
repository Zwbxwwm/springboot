package com.springboot.sspringboot.form;

import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductForm {

    private  String productId;

    private  String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private  String productDescription;

    private String productIcon;

    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

}
