package com.springboot.sspringboot.dto;

import com.springboot.sspringboot.entity.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    /**订单id*/
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private  String buyerOpenid;

    /**订单总金额*/
    private BigDecimal orderAmount;

    /**默认是新的订单*/
    private Integer orderStatus;

    private Integer payStatus;

    private Date createTime;

    private Date updateTime;

    private List<OrderDetail> orderDetailList;
}
