package com.springboot.sspringboot.config;

import lombok.Data;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class wechatAccountConfig {
    private  String mpAppId;
    private  String mpSecret;

    private String openAppId;
    private String openAppSecret;
    /*
    商户号
     */
    private String mchId;
    /*
    商户密钥
     */
    private String mchKey;
    /*
    商户证书
     */
    private String keyPath;
    /*
    微信异步通知地址
     */
    private String notifyUrl;
}
