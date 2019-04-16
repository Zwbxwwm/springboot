package com.springboot.sspringboot.handler;


import com.springboot.sspringboot.config.ProjectUrlConfig;
import com.springboot.sspringboot.exception.sellerAuthorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellerExceptionHandler {
    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    @ExceptionHandler(value = sellerAuthorException.class)
    public ModelAndView handlerSellerAuthorException(){
        return new ModelAndView("redirect:"+projectUrlConfig.getSellerExceptionUrl()+"/WxSpringboot/seller/user/loginCommon");
    }
}
