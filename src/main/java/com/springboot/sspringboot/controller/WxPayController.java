package com.springboot.sspringboot.controller;

import com.lly835.bestpay.model.PayResponse;
import com.springboot.sspringboot.Enum.ExceptionEnum;
import com.springboot.sspringboot.dto.OrderDTO;
import com.springboot.sspringboot.exception.sellException;
import com.springboot.sspringboot.service.IOrderService;
import com.springboot.sspringboot.service.IWxPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/pay")
public class WxPayController {
    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IWxPayService iWxPayService;

    @RequestMapping("/create")
    public ModelAndView create(@RequestParam("orderId")String orderId,
                               @RequestParam("returnUrl")String returnUrl,
                               Map<String,Object> map){
        OrderDTO orderDTO = iOrderService.findOne(orderId);
        if(orderDTO == null){
            throw  new sellException(ExceptionEnum.ORDER_NOT_EXIT);
        }
        PayResponse payResponse = iWxPayService.create(orderDTO);
        //开始实现支付功能
        map.put("payResponse",payResponse);
        map.put("returnUrl","https://www.xingluote.cn");
        return new ModelAndView("pay/create",map);
    }

    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData ){
        PayResponse payResponse = iWxPayService.notify(notifyData);
        return new ModelAndView("pay/success");
    }
}
