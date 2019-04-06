package com.springboot.sspringboot.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.springboot.sspringboot.Enum.ExceptionEnum;
import com.springboot.sspringboot.dto.OrderDTO;
import com.springboot.sspringboot.entity.OrderDetail;
import com.springboot.sspringboot.exception.sellException;
import com.springboot.sspringboot.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j

public class OrderForm2OrderDTOConverter {
    public static OrderDTO converter(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();
        Gson gson = new Gson();
        //开始转换
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
    try{
        List<OrderDetail> orderDetailList = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());
        orderDTO.setOrderDetailList(orderDetailList);
    }catch (Exception e){
        log.info("【json转换】错误，json:{}",orderForm.getItems());
        throw  new sellException(ExceptionEnum.ORDER_ERROR);
    }
        return orderDTO;
    }
}
