package com.springboot.sspringboot.dao;

import com.springboot.sspringboot.entity.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository infoRepository;

    @Test
    public void saveTest() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123");
        productInfo.setProductName("卤蛋");
        productInfo.setProductPrice(new BigDecimal(1.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("便宜");
        productInfo.setProductIcon("http://5b0988e595225.cdn.sohucs.com/images/20180519/188de848275941f4b8f47b4627d447fd.jpeg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(3);

        ProductInfo result =infoRepository.save(productInfo);
    }

    @Test
    public void findByProductStatusTest() {
        List<ProductInfo> list = infoRepository.findByProductStatus(0);
        Assert.assertNotEquals(0,list.size());
    }

}