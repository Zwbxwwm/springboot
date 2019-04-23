package com.springboot.sspringboot.service.Impl;

import com.springboot.sspringboot.Enum.OrderStatus;
import com.springboot.sspringboot.Enum.PayStatus;
import com.springboot.sspringboot.dto.OrderDTO;
import com.springboot.sspringboot.entity.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class orderServiceTest {

    public final static String OPENID = "10001";

    public final static String ORDER_ID = "15543770676411079148";

    @Autowired
    private orderService orderService;

    @Test
    public void create() throws Exception{
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("C7");
        orderDTO.setBuyerName("zwb");
        orderDTO.setBuyerPhone("5902056308");
        orderDTO.setBuyerOpenid(OPENID);

        List<OrderDetail> orderDetails = new ArrayList<>();

        OrderDetail example1 = new OrderDetail();
        example1.setProductId("321");
        example1.setProductQuantity(10);

        OrderDetail example2 = new OrderDetail();
        example2.setProductId("123");
        example2.setProductQuantity(10);

        orderDetails.add(example1);
        orderDetails.add(example2);
        orderDTO.setOrderDetailList(orderDetails);

        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】result = {}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOneTest() throws Exception {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        log.info("查询单个订单：{}",orderDTO);
        Assert.assertNotNull(orderDTO);
    }

    @Test
    public void findLis() {
        PageRequest request = PageRequest.of(0,1);
        Page<OrderDTO> orderDTOPage =orderService.findList(OPENID,request);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    public void cancelOrderTest() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(ORDER_ID);
        OrderDTO orderDTO1 =orderService.cancelOrder(orderDTO);
        Assert.assertNotEquals(null,orderDTO1);
    }

    @Test
    public void finishOrderTest() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO orderDTO1 =orderService.finishOrder(orderDTO);
        Assert.assertNotEquals(OrderStatus.FINISH_ORDER,orderDTO1.getPayStatus());
    }

    @Test
    public void payOrderTest() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO orderDTO1 =orderService.payOrder(orderDTO);
        Assert.assertNotEquals(PayStatus.HAVE_PAY,orderDTO1.getPayStatus());
    }
}