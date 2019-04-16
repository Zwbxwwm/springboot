package com.springboot.sspringboot.service.Impl;

import com.springboot.sspringboot.dto.OrderDTO;
import com.springboot.sspringboot.entity.SellerInfo;
import com.springboot.sspringboot.service.ISellerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Pageable;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class sellerServiceTest {
    private final static String OPENID ="abc";

    @Autowired
    private ISellerService iSellerService;

    @Test
    public void findLis() {
        PageRequest pageable = PageRequest.of(1,2);
        Page<OrderDTO> page =  iSellerService.findLis(pageable);
        Assert.assertTrue("【买家端】查询所有的订单",page.getTotalElements()>0);
    }

    @Test
    public void findSellerInfoByOpenidTest(){
        SellerInfo sellerInfo = iSellerService.findSellerInfoByOpenid(OPENID);
        Assert.assertNotNull(sellerInfo);
    }
}