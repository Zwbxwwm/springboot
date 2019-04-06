package com.springboot.sspringboot.service;

import com.springboot.sspringboot.entity.ProductCategory;

import java.util.List;

public interface ICategoryService {
    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> list);

    ProductCategory save(ProductCategory productCategory);
}
