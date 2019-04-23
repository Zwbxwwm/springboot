package com.springboot.sspringboot.service.Impl;

import com.springboot.sspringboot.Enum.ProductStatus;
import com.springboot.sspringboot.dto.CartDTO;
import com.springboot.sspringboot.entity.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class productInfoServiceTest {

    @Autowired
    private productInfoService productInfoService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productInfoService.findOne("1");
        Assert.assertEquals("1",productInfo.getProductId());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductInfo> list = productInfoService.findByProductStatus(ProductStatus.ON_SALE.getCode());
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void findAllTest(){
        PageRequest request = PageRequest.of(1,10);
        Page<ProductInfo> pages = productInfoService.findAll(request);
        Assert.assertNotEquals(0,pages.getTotalElements());
    }

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("321");
        productInfo.setProductName("红烧肉");
        productInfo.setProductPrice(new BigDecimal(1.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("还可以");
        productInfo.setProductIcon("http://xxxx.cn");
        productInfo.setProductStatus(ProductStatus.OUT_SALE.getCode());
        productInfo.setCategoryType(3);
        Assert.assertNotEquals(null,productInfoService.save(productInfo));
    }

    @Test
    public void decreaseStock() {
        List<CartDTO> cartDTOList = new ArrayList<>();
        CartDTO cartDTO1 = new CartDTO("321",10);
        cartDTOList.add(cartDTO1);
        productInfoService.decreaseStock(cartDTOList);
    }

    @Test
    public void off_productTest() {

    }
}