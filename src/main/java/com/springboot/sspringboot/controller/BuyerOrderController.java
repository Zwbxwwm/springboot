package com.springboot.sspringboot.controller;


import com.springboot.sspringboot.Enum.ExceptionEnum;
import com.springboot.sspringboot.Enum.ResponseCode;
import com.springboot.sspringboot.common.CheckUserIdentity;
import com.springboot.sspringboot.common.ServerResponse;
import com.springboot.sspringboot.converter.OrderForm2OrderDTOConverter;
import com.springboot.sspringboot.dto.OrderDTO;
import com.springboot.sspringboot.exception.sellException;
import com.springboot.sspringboot.form.OrderForm;
import com.springboot.sspringboot.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/order")
public class BuyerOrderController {
    @Autowired
    private IOrderService iOrderService;
    //创建订单
    @RequestMapping("/createOrder")
    public ServerResponse<Map<String,String>> createOrder(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("【创建订单】：订单参数不正确，orderForm = {}",orderForm);
            throw  new sellException(ResponseCode.ERROR_VALID.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = new OrderDTO();
        orderDTO = OrderForm2OrderDTOConverter.converter(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.info("【订单创建】：购物车为空， orderDetailList:{}",orderDTO.getOrderDetailList());
        }

        OrderDTO createResult = iOrderService.create(orderDTO);

        Map resultMap = new HashMap();
        resultMap.put("orderId",createResult.getOrderId());
        return ServerResponse.Success(resultMap);
    }

    //订单列表
    @GetMapping("/findList")
    public ServerResponse<List<OrderDTO>> findList(@RequestParam("openid")String openid,
                                                   @RequestParam(name = "page",defaultValue = "0") Integer page,
                                                   @RequestParam(name = "size",defaultValue = "10")Integer size){
        if(StringUtils.isEmpty(openid)){
            log.info("【查询订列表】：参数为空，openid = {}",openid);
            throw new sellException(ExceptionEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<OrderDTO> orderDTOPage =  iOrderService.findList(openid,pageRequest);

        return ServerResponse.Success(orderDTOPage.getContent());
    }

    //订单详情
    @GetMapping("/getDetailList")
    public ServerResponse getDetailList(@RequestParam("openid") String openid,
                                        @RequestParam("orderId") String orderId){

        OrderDTO orderDTO = iOrderService.findOne(orderId);
        if (CheckUserIdentity.check(openid, orderDTO)){
            return ServerResponse.Success(orderDTO);
        }else {
            throw new sellException(ExceptionEnum.IDENTITY_ERROR);
        }

    }


    //取消订单
    @PostMapping("/cancelOrder")
    public ServerResponse cancelOrder(@RequestParam("openid") String openid,
                                        @RequestParam("orderId") String orderId){
        OrderDTO orderDTO = iOrderService.findOne(orderId);
        if (CheckUserIdentity.check(openid, orderDTO)) {
            iOrderService.cancelOrder(orderDTO);
            return ServerResponse.Success();
        }else {
            throw new sellException(ExceptionEnum.IDENTITY_ERROR);
        }
    }

}
