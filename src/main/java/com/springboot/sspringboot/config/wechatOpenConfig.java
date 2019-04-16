package com.springboot.sspringboot.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class wechatOpenConfig {
    @Autowired
    private wechatAccountConfig wechatAccountConfig;


    @Bean
    public WxMpService wxOpenService(){
        WxMpService wxOpenService = new WxMpServiceImpl();
        wxOpenService.setWxMpConfigStorage(wxOpenConfigStorage());
        return wxOpenService;
    }

    @Bean
    public WxMpConfigStorage wxOpenConfigStorage(){
        WxMpInMemoryConfigStorage wxOpenInMemoryConfigStorage = new WxMpInMemoryConfigStorage();

        wxOpenInMemoryConfigStorage.setAppId(wechatAccountConfig.getOpenAppId());
        wxOpenInMemoryConfigStorage.setSecret(wechatAccountConfig.getOpenAppSecret());
        return wxOpenInMemoryConfigStorage;
    }

}
