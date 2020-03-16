package com.seckill.service;

import com.seckill.dao.OrderDao;
import com.seckill.dao.SeckillOrderDao;
import com.seckill.domain.OrderInfo;
import com.seckill.domain.SeckillOrder;
import com.seckill.domain.SeckillUser;
import com.seckill.redis.RedisService;
import com.seckill.redis.SeckillOrderKey;
import com.seckill.vo.GoodsVO;
import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.SecretKeyResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Albert on 2020/3/10.
 */
@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;

    @Autowired
    SeckillOrderDao seckillOrderDao;

    @Autowired
    RedisService redisService;

    public SeckillOrder getSkOrderByUserIdAndGoodsId(long userId, long goodsId) {
        String key = userId + "_" + goodsId;
        SeckillOrder seckillOrder = redisService.get(SeckillOrderKey.USER_GOODS,
                key, SeckillOrder.class);
        if (seckillOrder != null || redisService.exist(SeckillOrderKey.USER_GOODS, key))
            return seckillOrder;
        seckillOrder = orderDao.getSkOrderByUserIdAndGoodsId(userId, goodsId);
        if (seckillOrder == null)
            redisService.setNull(SeckillOrderKey.USER_GOODS, key);
        else
            redisService.set(SeckillOrderKey.USER_GOODS, key, seckillOrder);
        return seckillOrder;
    }

    @Transactional
    public OrderInfo createOrder(SeckillUser user, GoodsVO goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(user.getId());
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getSeckillPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderDao.insert(orderInfo);
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrder.setOrderId(orderInfo.getId());
        seckillOrderDao.insert(seckillOrder);
        redisService.set(SeckillOrderKey.USER_GOODS,
                seckillOrder.getUserId() + "_" + seckillOrder.getGoodsId(), seckillOrder);

        return orderInfo;
    }

    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }
}
