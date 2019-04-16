package com.springboot.sspringboot.service.Impl;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.springboot.sspringboot.config.wechatPayConfig;
import com.springboot.sspringboot.dto.OrderDTO;
import com.springboot.sspringboot.service.IOrderService;
import com.springboot.sspringboot.service.IWxPayService;
import com.springboot.sspringboot.utils.BigDecimalUtil;
import com.springboot.sspringboot.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class wxPayService implements IWxPayService {

    private  final  static String ORDER_NAME="微信点单";
    @Autowired
    private BestPayServiceImpl bestPayService;
    @Autowired
    private IOrderService iOrderService;

    @Override
    public PayResponse create(OrderDTO orderDTO){
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】发起支付，request={}", JsonUtil.toJson(payRequest));
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付，Response={}",JsonUtil.toJson(payResponse));
        return payResponse;
    }

    public PayResponse notify(String notifyData){
        //1.验证签名
        //2.支付状态
        //3.支付金额
        //4.支付者
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知，payResponse={}",payResponse);
        //减库存
        OrderDTO orderDTO =  iOrderService.findOne(payResponse.getOrderId());
        if(!BigDecimalUtil.equal(orderDTO.getOrderAmount().doubleValue(),payResponse.getOrderAmount())){
            log.error("【微信支付】异步通知，订单金额不一致，orderId={},微信通知金额={},系统金额={}",
                    orderDTO.getOrderId(),payResponse.getOrderAmount(),orderDTO.getOrderAmount());
        }
        iOrderService.payOrder(orderDTO);
        return payResponse;
    }

    public RefundResponse refund(OrderDTO orderDTO){
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】 refundRequest={}",JsonUtil.toJson(refundRequest));
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】 refundResponse={}",JsonUtil.toJson(refundResponse));
        return refundResponse;
    }
}
