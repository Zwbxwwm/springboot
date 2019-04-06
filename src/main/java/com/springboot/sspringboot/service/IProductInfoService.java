package com.springboot.sspringboot.service;

import com.springboot.sspringboot.dto.CartDTO;
import com.springboot.sspringboot.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductInfoService {
    ProductInfo findOne(String productId);


    /**
     * 查询上架状态的商品
     * @param productStatus
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer productStatus);

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    void increaseStock(List<CartDTO> cartDTOList);

    void decreaseStock(List<CartDTO> cartDTOList);
}
