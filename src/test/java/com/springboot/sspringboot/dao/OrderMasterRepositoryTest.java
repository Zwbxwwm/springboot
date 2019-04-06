package com.springboot.sspringboot.dao;

import com.springboot.sspringboot.entity.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Pageable;
import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    private  OrderMasterRepository orderMasterRepository;
    private  final  String OPENID = "11010";
    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("12345621");
        orderMaster.setBuyerName("张三");
        orderMaster.setBuyerPhone("15902056308");
        orderMaster.setBuyerAddress("c7");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(2.3));
        OrderMaster orderMaster1 = orderMasterRepository.save(orderMaster);
        Assert.assertNotEquals(null,orderMaster1);
    }

    @Test
    public void findByBuyerOpenidTest() {
        PageRequest pageRequest = PageRequest.of(0,1);
        Page<OrderMaster> result = orderMasterRepository.findByBuyerOpenid(OPENID,pageRequest);
        Assert.assertNotEquals(0,result.getTotalElements());
    }
}