package com.springboot.sspringboot.service;

import com.springboot.sspringboot.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderService {
    /**创建订单*/
    OrderDTO create(OrderDTO orderDTO);

    /**查询单个订单*/
    OrderDTO findOne(String orderId);

    /**查询订单列表订单*/
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /**取消订单*/
    OrderDTO cancelOrder(OrderDTO orderDTO);

    /**完结订单*/
    OrderDTO finishOrder(OrderDTO orderDTO);

    /**支付订单*/
    OrderDTO payOrder(OrderDTO orderDTO);

}
