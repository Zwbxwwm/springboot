package com.springboot.sspringboot.controller;

import com.springboot.sspringboot.Enum.ExceptionEnum;
import com.springboot.sspringboot.Enum.OrderStatus;
import com.springboot.sspringboot.Enum.ResponseCode;
import com.springboot.sspringboot.dto.OrderDTO;
import com.springboot.sspringboot.exception.sellException;
import com.springboot.sspringboot.service.IOrderService;
import com.springboot.sspringboot.service.ISellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/seller")
public class SellerOrderController {

    @Autowired
    private ISellerService iSellerService;

    @Autowired
    private IOrderService iOrderService;

    @GetMapping("/findList")
    public ModelAndView findList(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                 @RequestParam(value = "size",defaultValue = "10") Integer size,
                                 Map<String,Object> map){
        PageRequest pageRequest = PageRequest.of(page-1,size);
        Page<OrderDTO> orderDTOPage = iSellerService.findList(pageRequest);
        map.put("orderDTOPage",orderDTOPage);
        map.put("currentPage",page);
        return new ModelAndView("order/list",map);
    }

    @GetMapping("cancel")
    public ModelAndView cancel(@RequestParam("orderId")String orderId,Map<String,Object> map){
        OrderDTO orderDTO =null;
        try {
            orderDTO = iOrderService.findOne(orderId);
        } catch (sellException e) {
            map.put("error_message", e.getMessage());
            map.put("url","/WxSpringboot/seller/findList");
            return new ModelAndView("/common/error",map);
        }
        iOrderService.cancelOrder(orderDTO);
        map.put("success_message", ResponseCode.SUCCESS.getMsg());
        map.put("url","/WxSpringboot/seller/findList");
        return new ModelAndView("/common/success",map);
    }

    @GetMapping("detail")
    public ModelAndView detail(@RequestParam("orderId")String orderId,Map<String,Object> map){
        OrderDTO orderDTO = new OrderDTO();
        try {
           orderDTO = iOrderService.findOne(orderId);
        } catch (sellException e) {
            map.put("error_message", e.getMessage());
            map.put("url","/WxSpringboot/seller/findList");
            return new ModelAndView("/common/error",map);
        }

        map.put("orderDTO",orderDTO);
        return new ModelAndView("order/detail",map);
    }

    @GetMapping("finish")
    public ModelAndView finish(@RequestParam("orderId")String orderId,Map<String,Object> map){
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = iOrderService.findOne(orderId);
        } catch (sellException e) {
            map.put("error_message", e.getMessage());
            map.put("url","/WxSpringboot/seller/findList");
            return new ModelAndView("/common/error",map);
        }
        orderDTO = iOrderService.finishOrder(orderDTO);
        map.put("success_message", ResponseCode.SUCCESS.getMsg());
        map.put("url","/WxSpringboot/seller/findList");
        return new ModelAndView("/common/success",map);
    }
}
