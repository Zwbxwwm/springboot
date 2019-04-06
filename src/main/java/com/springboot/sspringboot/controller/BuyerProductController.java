package com.springboot.sspringboot.controller;


import com.springboot.sspringboot.Vo.ProductInfoVo;
import com.springboot.sspringboot.Vo.ProductVo;
import com.springboot.sspringboot.Enum.ProductStatus;
import com.springboot.sspringboot.Enum.ResponseCode;
import com.springboot.sspringboot.common.ServerResponse;
import com.springboot.sspringboot.entity.ProductCategory;
import com.springboot.sspringboot.entity.ProductInfo;
import com.springboot.sspringboot.service.ICategoryService;
import com.springboot.sspringboot.service.IProductInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private IProductInfoService iProductInfoService;

    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping("/list")
    public ServerResponse getAll(){
//        查询所有上架的商品
        List<ProductInfo> productInfoList = iProductInfoService.findByProductStatus(ProductStatus.ON_SALE.getCode());

//        查找所有的类目
        List<Integer> categoryTypeList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> categoryList = iCategoryService.findByCategoryTypeIn(categoryTypeList);

        //拼接
        List<ProductVo> productVoList = new ArrayList<>();
        for(ProductCategory productCategory:categoryList){
            ProductVo productVo = new ProductVo();
            productVo.setCategoryName(productCategory.getCategoryName());
            productVo.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVo> productInfoVoList = new ArrayList<>();
            for(ProductInfo productInfo:productInfoList){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo,productInfoVo);
                    productInfoVoList.add(productInfoVo);
                }
            }
            productVo.setProductInfoVoList(productInfoVoList);

            productVoList.add(productVo);
        }
        return ServerResponse.Success(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),productVoList);


    }
}
