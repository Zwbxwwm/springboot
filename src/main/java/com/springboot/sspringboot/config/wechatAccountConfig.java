package com.springboot.sspringboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class wechatAccountConfig {
    private  String mpAppId;
    private  String mpSecret;
}
