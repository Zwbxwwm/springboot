package com.springboot.sspringboot.controller;


import com.springboot.sspringboot.Enum.ExceptionEnum;
import com.springboot.sspringboot.config.wechatMpConfig;
import com.springboot.sspringboot.exception.sellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

@RestController
@RequestMapping("/login")
@Slf4j
public class WxLoginController {
    @Autowired
    private WxMpService wxMpService;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        String url = "http://www.xingluote.cn";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url,WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(returnUrl));
        //1、配置
        //2、调用方法
        return "redirect:"+redirectUrl;

    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code")String code,
                           @RequestParam("state")String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}",e);
            throw new sellException(ExceptionEnum.WX_LOGIN_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        return "redirect:"+returnUrl+"?openid="+openId;
    }
}

