package com.springboot.sspringboot.controller;


import com.springboot.sspringboot.Enum.ExceptionEnum;
import com.springboot.sspringboot.Enum.ProductStatus;
import com.springboot.sspringboot.Enum.ResponseCode;
import com.springboot.sspringboot.dto.OrderDTO;
import com.springboot.sspringboot.entity.ProductCategory;
import com.springboot.sspringboot.entity.ProductInfo;
import com.springboot.sspringboot.exception.sellException;
import com.springboot.sspringboot.form.ProductForm;
import com.springboot.sspringboot.service.ICategoryService;
import com.springboot.sspringboot.service.IProductInfoService;
import com.springboot.sspringboot.utils.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/seller/product")
public class SellerProductController {
    @Autowired
    private IProductInfoService iProductInfoService;

    @Autowired
    private ICategoryService iCategoryService;

    @GetMapping("findList")
    public ModelAndView findList(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                 @RequestParam(value = "size",defaultValue = "10") Integer size,
                                 Map<String,Object> map){
        PageRequest pageRequest = PageRequest.of(page-1,size);
        Page<ProductInfo> productInfos = iProductInfoService.findAll(pageRequest);
        map.put("productInfoPage",productInfos);
        map.put("currentPage",page);
        return new ModelAndView("product/list",map);
    }

    @GetMapping("/off_product")
    public ModelAndView off_product(@RequestParam(value = "productId") String productId,
                                    Map<String,Object> map){
        iProductInfoService.off_product(productId);
        map.put("success_msg", ResponseCode.SUCCESS.getMsg());
        map.put("url","/WxSpringboot/seller/product/findList");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/on_product")
    public ModelAndView on_product(@RequestParam("productId") String productId,
                                    Map<String,Object> map){
        iProductInfoService.on_product(productId);
        map.put("success_msg", ResponseCode.SUCCESS.getMsg());
        map.put("url","/WxSpringboot/seller/product/findList");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",required = false) String productId,
                              Map<String,Object> map){
        if(!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = iProductInfoService.findOne(productId);
            map.put("productInfo",productInfo);
        }
        List<ProductCategory> categoryList = iCategoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("product/index",map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm, BindingResult bindingResult,Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("error_message",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/WxSpringboot/seller/findList");
            return new ModelAndView("common/error",map);
        }
        ProductInfo productInfo = new ProductInfo();
        try{

            if(!StringUtils.isEmpty(productForm.getProductId())){
                productInfo = iProductInfoService.findOne(productForm.getProductId());
            }else{
                productForm.setProductId(IdUtil.createUniqueKey());
            }
            BeanUtils.copyProperties(productForm,productInfo);
            iProductInfoService.save(productInfo);
        }catch (Exception e){
            map.put("error_message",e.getMessage());
            map.put("url","/WxSpringboot/seller/findList");
            return new ModelAndView("common/error",map);
        }
        map.put("success_msg", ResponseCode.SUCCESS.getMsg());
        map.put("url","/WxSpringboot/seller/product/findList");
        return new ModelAndView("common/success",map);
    }
}
