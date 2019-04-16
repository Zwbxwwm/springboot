package com.springboot.sspringboot.service.Impl;

import com.springboot.sspringboot.Enum.ExceptionEnum;
import com.springboot.sspringboot.Enum.ProductStatus;
import com.springboot.sspringboot.dao.ProductInfoRepository;
import com.springboot.sspringboot.dto.CartDTO;
import com.springboot.sspringboot.entity.ProductInfo;
import com.springboot.sspringboot.exception.sellException;
import com.springboot.sspringboot.service.IProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class productInfoService implements IProductInfoService {
    @Autowired
    private ProductInfoRepository infoRepository;

    @Override
    public ProductInfo findOne(String productId) {
        return infoRepository.findById(productId).get();
    }

    @Override
    public List<ProductInfo> findByProductStatus(Integer productStatus) {
        return infoRepository.findByProductStatus(productStatus);
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable){
        return infoRepository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return infoRepository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo = infoRepository.findById(cartDTO.getProductId()).get();
            if(productInfo == null){
                throw new sellException(ExceptionEnum.NOT_EXIT);
            }
            Integer resultStock = productInfo.getProductStock()+cartDTO.getProductQuantity();
            productInfo.setProductStock(resultStock);
            infoRepository.save(productInfo);
        }
    }

    @Override
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo = infoRepository.findById(cartDTO.getProductId()).get();
            if(productInfo == null){
                throw new sellException(ExceptionEnum.NOT_EXIT);
            }
            Integer resultStock = productInfo.getProductStock()-cartDTO.getProductQuantity();
            if(resultStock<0){
                throw new sellException(ExceptionEnum.UNDER_STOCK);
            }
            productInfo.setProductStock(resultStock);
            infoRepository.save(productInfo);
        }
    }

    public void off_product(String productId){
        ProductInfo productInfo = infoRepository.findById(productId).get();
        if(productInfo==null){
            log.info("【下架产品】该商品不存在，productId={}",productInfo.getProductId());
            throw new sellException(ExceptionEnum.NOT_EXIT);
        }
        productInfo.setProductStatus(ProductStatus.OUT_SALE.getCode());
        infoRepository.save(productInfo);
    }

    public void on_product(String productId){
        ProductInfo productInfo =infoRepository.findById(productId).get();
        if(productInfo==null){
            log.info("【下架产品】该商品不存在，productId={}",productInfo.getProductId());
            throw new sellException(ExceptionEnum.NOT_EXIT);
        }
        productInfo.setProductStatus(ProductStatus.ON_SALE.getCode());
        infoRepository.save(productInfo);
    }
}
