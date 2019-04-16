package com.springboot.sspringboot.dao;


import com.springboot.sspringboot.entity.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<SellerInfo,String> {
    SellerInfo findByOpenid(String openid);
}
