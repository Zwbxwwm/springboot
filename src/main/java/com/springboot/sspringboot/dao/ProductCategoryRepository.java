package com.springboot.sspringboot.dao;

import com.springboot.sspringboot.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    List<ProductCategory>  findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory findByCategoryType(String categoryType);
}
