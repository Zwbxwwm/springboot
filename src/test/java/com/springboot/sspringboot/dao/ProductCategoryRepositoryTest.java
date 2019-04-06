package com.springboot.sspringboot.dao;

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
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void findOneTest(){
        ProductCategory productCategory = productCategoryRepository.findById(1).get();
        System.out.println(productCategory);
    }

    @Test
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory("宵夜",3);
        productCategoryRepository.save(productCategory);
    }

    @Test
    @Transactional
    public void updateTest(){
        ProductCategory productCategory = new ProductCategory("面类",5);
        ProductCategory productCategory1 = productCategoryRepository.save(productCategory);
        Assert.assertNotEquals(null,productCategory1);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList(2,3,4);
        List<ProductCategory> productCategories = productCategoryRepository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,productCategories.size());
    }

}