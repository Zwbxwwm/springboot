package com.springboot.sspringboot.utils;

import com.springboot.sspringboot.constant.CookieConstant;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieUtil {
    /**
     * 设置cookie
     * @param httpServletResponse
     * @param name
     * @param value
     * @param time
     */
    public static void setCookie(HttpServletResponse httpServletResponse,
                                 String name,String value,Integer time){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(CookieConstant.TIME_OUT);
        httpServletResponse.addCookie(cookie);
    }

    public static Cookie getCookie(HttpServletRequest request,String name){
        Map<String,Cookie> cookieMap = readCookieMap(request);
        if(cookieMap.containsKey(name)){
            return cookieMap.get(name);
        }else{
            return null;
        }
    }

    private static Map<String,Cookie> readCookieMap(HttpServletRequest httpServletRequest) {
        Map<String,Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;
    }
}
