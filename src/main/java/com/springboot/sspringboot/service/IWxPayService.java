package com.springboot.sspringboot.service;

import com.lly835.bestpay.model.PayResponse;
import com.springboot.sspringboot.dto.OrderDTO;

public interface IWxPayService {
    PayResponse create(OrderDTO orderDTO);

    PayResponse notify(String notifyData );
}
