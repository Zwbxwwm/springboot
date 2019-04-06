package com.springboot.sspringboot.entity;


import com.springboot.sspringboot.Enum.OrderStatus;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate()
public class OrderMaster {

    /**订单id*/
    @Id
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private  String buyerOpenid;

    /**订单总金额*/
    private BigDecimal orderAmount;

    /**默认是新的订单*/
    private Integer orderStatus = OrderStatus.NEW_ORDER.getCode();

    private Integer payStatus =OrderStatus.UN_PAY.getCode();

    private Date createTime;

    private Date updateTime;


}
