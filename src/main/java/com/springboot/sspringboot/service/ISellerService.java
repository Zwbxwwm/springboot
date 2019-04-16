package com.springboot.sspringboot.service;

import com.springboot.sspringboot.dto.OrderDTO;
import com.springboot.sspringboot.entity.SellerInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISellerService {
    /**查询订单列表
     *
     * @param pageable
     * @return
     */
    Page<OrderDTO> findLis(Pageable pageable);

    /** SESSION
     * 根据用户openid查询用户基本信息
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);

}
