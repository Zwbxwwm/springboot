package com.springboot.sspringboot.service.Impl;

import com.springboot.sspringboot.Enum.ExceptionEnum;
import com.springboot.sspringboot.Enum.OrderStatus;
import com.springboot.sspringboot.Enum.PayStatus;
import com.springboot.sspringboot.converter.OrderDetail2CartDTOConverter;
import com.springboot.sspringboot.converter.OrderMaster2OrderDTOConverter;
import com.springboot.sspringboot.dao.OrderDetailRepository;
import com.springboot.sspringboot.dao.OrderMasterRepository;
import com.springboot.sspringboot.dto.CartDTO;
import com.springboot.sspringboot.dto.OrderDTO;
import com.springboot.sspringboot.entity.OrderDetail;
import com.springboot.sspringboot.entity.OrderMaster;
import com.springboot.sspringboot.entity.ProductInfo;
import com.springboot.sspringboot.exception.sellException;
import com.springboot.sspringboot.service.IOrderService;
import com.springboot.sspringboot.service.IWebSocket;
import com.springboot.sspringboot.service.IWxPayService;
import com.springboot.sspringboot.utils.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class orderService implements IOrderService {

    @Autowired
    private productInfoService productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private IWxPayService iWxPayService;

    @Autowired
    private IWebSocket iWebSocket;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = IdUtil.createUniqueKey();
        BigDecimal totalPrice =new BigDecimal(BigInteger.ZERO);
        for(OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            //查询商品的数量、价格
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if(productInfo==null){
                throw  new sellException(ExceptionEnum.NOT_EXIT);
            }
            //查询订单的总价
            totalPrice=productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(totalPrice);

            orderDetail.setDetailId(IdUtil.createUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);
        }
        //查询商品的库存
        OrderMaster orderMaster = new OrderMaster();
        
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(totalPrice);
        orderMaster.setOrderStatus(OrderStatus.NEW_ORDER.getCode());
        orderMaster.setPayStatus(PayStatus.UN_PAY.getCode());
        orderMasterRepository.save(orderMaster);
        //减去库存
        List<CartDTO> cartDTOList = new ArrayList<>();
        cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);

        //发送新订单消息到后台
        iWebSocket.sendMessage(orderDTO.getOrderId());
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderDTO orderDTO =new OrderDTO();
        OrderMaster orderMaster = null;
        try {
            orderMaster = orderMasterRepository.findById(orderId).get();
        } catch (Exception e) {
            throw new sellException(ExceptionEnum.ORDER_NOT_EXIT);
        }

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);

        if(CollectionUtils.isEmpty(orderDetails)){
            throw new sellException(ExceptionEnum.ORDER_NOT_EXIT);
        }

        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetails);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findLis(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> pageable1 = new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return pageable1;
    }

    @Override
    public OrderDTO cancelOrder(OrderDTO orderDTO) {
        String orderId = orderDTO.getOrderId();
        //获取用户的订单总表
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get() ;
        //判断订单完结，否则设置订单为取消状态
        if(!orderMaster.getOrderStatus().equals(OrderStatus.NEW_ORDER.getCode())){
            log.info("【取消订单】 订单状态不对,orderId = {}",orderMaster.getOrderId());
            throw new sellException(ExceptionEnum.FINISHED);
        }
        //判断订单支付状态
//        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
//            log.info("【取消订单】 订单中无商品详情: orderDetail = {}",orderDTO.getOrderDetailList());
//            throw new sellException(ExceptionEnum.ORDER_DETAIL_NOT_EXIT);
//        }
        orderMaster.setOrderStatus(OrderStatus.CANCEL_ORDER.getCode());
        orderDTO = OrderMaster2OrderDTOConverter.convert(orderMaster);
        //转成购物车DTO状态，方便把库存加上
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        List<CartDTO> cartDTOList =  OrderDetail2CartDTOConverter.convert(orderDetailList);
        productInfoService.increaseStock(cartDTOList);
        if(orderMaster.getPayStatus().equals(PayStatus.HAVE_PAY)){
            iWxPayService.refund(orderDTO);
        }
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }

    @Override
    public OrderDTO finishOrder(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatus.NEW_ORDER.getCode())){
            log.info("【完结订单】 订单状态错误，orderStatus = {}",orderDTO.getOrderStatus());
            throw  new sellException(ExceptionEnum.ORDER_ERROR);
        }
        OrderMaster orderMaster = orderMasterRepository.findById(orderDTO.getOrderId()).get();
        //修改订单的状态
        orderMaster.setOrderStatus(OrderStatus.FINISH_ORDER.getCode());
        orderMaster = orderMasterRepository.save(orderMaster);
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    @Override
    public OrderDTO payOrder(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getPayStatus().equals(OrderStatus.NEW_ORDER.getCode())){
            log.info("【完结订单】 订单状态错误，payStatus = {}",orderDTO.getPayStatus());
            throw  new sellException(ExceptionEnum.ORDER_ERROR);
        }
        if(!orderDTO.getPayStatus().equals(PayStatus.UN_PAY.getCode())){
            log.info("【订单支付】 该订单已支付, orderId={},orderPayStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new sellException(ExceptionEnum.ORDER_ERROR);
        }

        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatus.HAVE_PAY.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }
}
