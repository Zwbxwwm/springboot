package com.springboot.sspringboot.controller;

import com.springboot.sspringboot.Enum.ResponseCode;
import com.springboot.sspringboot.entity.ProductCategory;
import com.springboot.sspringboot.form.CategoryForm;
import com.springboot.sspringboot.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/seller/category")

public class SellerCategoryController {
    @Autowired
    private ICategoryService iCategoryService;

    @GetMapping("/findList")
    public ModelAndView findList(Map<String,Object> map){
        List<ProductCategory> categoryList = iCategoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("category/list",map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId",required = false) Integer categoryId,Map<String,Object> map){
        if(categoryId!=null){
            ProductCategory category = iCategoryService.findOne(categoryId);
            map.put("category",category);
        }
        return new ModelAndView("category/index",map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String,Object> map){

        if(bindingResult.hasErrors()){
            map.put("error_message",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/WxSpringboot/seller/category/findList");
            return new ModelAndView("common/error",map);
        }
        ProductCategory productCategory = new ProductCategory();
        try {
            if(categoryForm.getCategoryId()!=null){
                productCategory = iCategoryService.findOne(categoryForm.getCategoryId());
            }
            BeanUtils.copyProperties(categoryForm,productCategory);
            iCategoryService.save(productCategory);
        } catch (Exception e) {
            map.put("error_message",e.getMessage());
            map.put("url","/WxSpringboot/seller/category/findList");
            return new ModelAndView("common/error",map);
        }
        map.put("success_msg", ResponseCode.SUCCESS.getMsg());
        map.put("url","/WxSpringboot/seller/category/findList");
        return new ModelAndView("/common/success",map);
    }
}
