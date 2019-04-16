package com.springboot.sspringboot.aspectAuthorCheck;

import com.springboot.sspringboot.constant.CookieConstant;
import com.springboot.sspringboot.constant.RedisConstant;
import com.springboot.sspringboot.exception.sellException;
import com.springboot.sspringboot.exception.sellerAuthorException;
import com.springboot.sspringboot.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class sellerAspectAuthor {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.springboot.sspringboot.controller.Seller*.*(..))"+"&& !execution(public * com.springboot.sspringboot.controller.SellerUserController.*(..))")
    public void verify(){

    }
    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();


        //查询cookie
        Cookie cookie = CookieUtil.getCookie(request, CookieConstant.NAME);
        if(cookie == null){
            log.warn("【登陆校验】Cookie中没有找到token值");
            throw new sellerAuthorException();
        }

        String token = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));

        if(StringUtils.isEmpty(token)){
            log.warn("【登陆校验】redis中无法获得该token");
            throw  new sellerAuthorException();
        }
    }
}
