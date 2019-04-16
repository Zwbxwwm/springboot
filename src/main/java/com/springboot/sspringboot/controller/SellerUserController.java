package com.springboot.sspringboot.controller;


import com.lly835.bestpay.rest.type.Post;
import com.springboot.sspringboot.Enum.ExceptionEnum;
import com.springboot.sspringboot.Enum.ResponseCode;
import com.springboot.sspringboot.config.ProjectUrlConfig;
import com.springboot.sspringboot.constant.CookieConstant;
import com.springboot.sspringboot.constant.RedisConstant;
import com.springboot.sspringboot.entity.SellerInfo;
import com.springboot.sspringboot.service.ISellerService;
import com.springboot.sspringboot.utils.CookieUtil;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller/user")
public class SellerUserController {

    @Autowired
    private ISellerService iSellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid")String openid,
                              HttpServletResponse httpServletResponse,
                              Map<String,Object> map){
        SellerInfo sellerInfo = iSellerService.findSellerInfoByOpenid(openid);
        if(sellerInfo==null){
            map.put("error_message", ExceptionEnum.WX_LOGIN_ERROR.getMsg());
            map.put("url","/WxSpringboot/seller/product/findList");
            return new ModelAndView("/common/error",map);
        }

        String token = UUID.randomUUID().toString();
        Integer TimeOut = RedisConstant.TIME_OUT;

        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,TimeOut, TimeUnit.SECONDS);
        CookieUtil.setCookie(httpServletResponse,CookieConstant.NAME,token,CookieConstant.TIME_OUT);
        return new ModelAndView("redirect:"+projectUrlConfig.getSellerLoginSuccess()+"/WxSpringboot/seller/findList");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,Map<String,Object> map){
        Cookie cookie = CookieUtil.getCookie(httpServletRequest,CookieConstant.NAME);
        //清除redis，
        //清除cookie
        if(cookie!=null){
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            CookieUtil.setCookie(httpServletResponse,CookieConstant.NAME,null,0);
        }
        map.put("success_msg", ResponseCode.LOGOUT.getMsg());
        map.put("url","/WxSpringboot/seller/findList");
        return new ModelAndView("/common/success",map);
    }

    @GetMapping("/loginCommon")
    public ModelAndView loginCommon(){
        return new ModelAndView("/common/login");
    }
}
