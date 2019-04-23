package com.springboot.sspringboot.dao;

import com.springboot.sspringboot.entity.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderDetailRepositoryTest {
    @Autowired
    private  OrderDetailRepository orderDetailRepository;

    @Test
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1221456");
        orderDetail.setProductName("钵仔糕");
        orderDetail.setOrderId("123");
        orderDetail.setProductIcon("https://xxxx.cn");
        orderDetail.setProductQuantity(2);
        orderDetail.setProductId("12343");
        orderDetail.setProductPrice(new BigDecimal(3.5));
        OrderDetail orderDetail1 = orderDetailRepository.save(orderDetail);
        Assert.assertNotNull(orderDetail);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetails =  orderDetailRepository.findByOrderId("123");
//        System.out.println(orderDetails);
        Assert.assertNotEquals(0,orderDetails.size());

    }
}