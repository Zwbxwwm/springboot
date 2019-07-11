package com.springboot.sspringboot.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Http请求工具类
 * @author
 * @since
 * @version
 */
public class HttpRequestUtil {

    public static String client(String url, HttpMethod method, MultiValueMap<String,String> param){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
        return response.getBody();
    }


    public static String doPostForJson(String url, String jsonParams){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(180 * 1000).setConnectionRequestTimeout(180 * 1000)
                .setSocketTimeout(180 * 1000).setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type","application/json");  //
        try {
            String str = null;
            StringEntity se = new StringEntity(jsonParams);
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);
            if ("200".equals(String.valueOf(response.getStatusLine().getStatusCode()))) {
                HttpEntity entity = response.getEntity();
                str = EntityUtils.toString(entity);
            }
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return "post failure :caused by-->" + e.getMessage().toString();
        }finally {
            if(null != httpClient){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {
        String url = "http://api.es.xiaojukeji.com/webapp/ticket/fetch";
        Map<String,String> param = new HashMap();
        param.put("client_id","141b476b6c59a928cd7297ac15d0b74f_test");
        param.put("data_encode","Xe9g7NuVYHhuZ9B4EkBeqNLUmBhJpHcK4x74bq6jN2sVFTTh/X/phuEEqXFORnAbexvE" +
                "fXTudqr457ZLbYieiTBElkhgrDZEwgxlCR07iiCMsmZNNwN6GYxLsartEShLDNcWXNGTqQ+gSFOpAGa6w54Pa2MWFtTUgXu" +
                "5saNfyEQ3o27UacOSxB6WkcQXU5FsCzySOoEDGMJ5/bP/JCzJdZMDG+IfBv/5OKce5GV2b7jfmEfC8HCaiA==");
        String param2 = JSON.toJSONString(param);
        String result = doPostForJson(url,param2);
        JSONObject jsonObject = JSON.parseObject(result);

        Object data = jsonObject.get("data");
        System.out.println(jsonObject);
    }
}