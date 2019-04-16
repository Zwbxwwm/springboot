package com.springboot.sspringboot.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.springboot.sspringboot.dto.OrderDTO;

public interface IWxPayService {
    PayResponse create(OrderDTO orderDTO);

    PayResponse notify(String notifyData );

    RefundResponse refund(OrderDTO orderDTO);
}
