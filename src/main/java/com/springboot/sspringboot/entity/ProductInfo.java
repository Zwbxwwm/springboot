package com.springboot.sspringboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.sspringboot.Enum.OrderStatus;
import com.springboot.sspringboot.Enum.PayStatus;
import com.springboot.sspringboot.Enum.ProductStatus;
import com.springboot.sspringboot.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = 6164701397173894194L;
    @Id
    private  String productId;

    private  String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private  String productDescription;

    private String productIcon;

    private Integer productStatus = ProductStatus.ON_SALE.getCode();

    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductStatus getProductStatusEnum( ){
        return EnumUtil.getEnumByCode(productStatus, ProductStatus.class);
    }

}
