package com.springboot.sspringboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by 廖师兄
 * 2017-07-30 11:43
 */
@Data
@Component
@ConfigurationProperties(prefix = "projecturl")
public class ProjectUrlConfig {

    /**
     * 成功登陆跳转地址
     */
    private String sellerLoginSuccess;

    private  String sellerExceptionUrl;
}
