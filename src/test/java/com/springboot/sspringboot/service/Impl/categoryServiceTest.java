package com.springboot.sspringboot.service.Impl;

import com.springboot.sspringboot.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class categoryServiceTest {

    @Autowired
    private categoryService categoryService;

    @Test
    public void findOne() {
        ProductCategory productCategory = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> productCategoryList = categoryService.findAll();
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<Integer> list = Arrays.asList(2,3,4);
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    @Transactional
    public void save() {
        ProductCategory productCategory = new ProductCategory("测试",100);
        ProductCategory productCategory1 = categoryService.save(productCategory);
        Assert.assertNotNull(productCategory1);
    }
}