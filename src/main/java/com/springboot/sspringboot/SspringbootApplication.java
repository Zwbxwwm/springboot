package com.springboot.sspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SspringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SspringbootApplication.class, args);
    }

}
