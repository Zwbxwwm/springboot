package com.springboot.sspringboot.dao;

import com.springboot.sspringboot.entity.SellerInfo;
import com.springboot.sspringboot.utils.IdUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerRepositoryTest {
    @Autowired
    private SellerRepository sellerRepository;

    @Test
    public void save(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(IdUtil.createUniqueKey());
        sellerInfo.setPassword("123456");
        sellerInfo.setUsername("admin");
        sellerInfo.setOpenid("abc");
        SellerInfo sellerInfo1 = sellerRepository.save(sellerInfo);
        Assert.assertNotNull(sellerInfo1);
    }

    @Test
    public void findByOpenid(){
        SellerInfo sellerInfo = sellerRepository.findByOpenid("abc");
        Assert.assertNotNull(sellerInfo);
    }

}