package com.springboot.sspringboot.converter;

import com.springboot.sspringboot.dto.CartDTO;
import com.springboot.sspringboot.entity.OrderDetail;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDetail2CartDTOConverter {

    public static CartDTO convert(OrderDetail orderDetail){
        CartDTO cartDTO = new CartDTO();
        BeanUtils.copyProperties(orderDetail,cartDTO);
        return cartDTO;
    }

    public static List<CartDTO> convert(List<OrderDetail> orderDetailList){
        return orderDetailList.stream().map(e ->convert(e)).collect(Collectors.toList());
    }
}
