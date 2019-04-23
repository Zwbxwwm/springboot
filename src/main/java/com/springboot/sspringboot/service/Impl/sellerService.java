package com.springboot.sspringboot.service.Impl;

import com.springboot.sspringboot.Enum.ExceptionEnum;
import com.springboot.sspringboot.Enum.OrderStatus;
import com.springboot.sspringboot.converter.OrderMaster2OrderDTOConverter;
import com.springboot.sspringboot.dao.OrderMasterRepository;
import com.springboot.sspringboot.dao.SellerRepository;
import com.springboot.sspringboot.dto.OrderDTO;
import com.springboot.sspringboot.entity.OrderMaster;
import com.springboot.sspringboot.entity.SellerInfo;
import com.springboot.sspringboot.exception.sellException;
import com.springboot.sspringboot.service.ISellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class sellerService implements ISellerService {
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private SellerRepository sellerRepository;

    public Page<OrderDTO> findList(Pageable pageable){
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> pageable1 = new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return pageable1;
    }

    public SellerInfo findSellerInfoByOpenid(String openid){
        return sellerRepository.findByOpenid(openid);
    }


}
