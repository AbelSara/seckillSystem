package com.seckill.service;

import com.seckill.dao.OrderDao;
import com.seckill.domain.Goods;
import com.seckill.domain.OrderInfo;
import com.seckill.domain.SeckillOrder;
import com.seckill.domain.SeckillUser;
import com.seckill.redis.RedisService;
import com.seckill.redis.SeckillKey;
import com.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Albert on 2020/3/10.
 */
@Service
public class SeckillService {
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo secKill(SeckillUser user, GoodsVO goods) {
        //减库存
        boolean succ = goodsService.reduceStock(goods);
        //下订单，写入订单表和秒杀订单表
        OrderInfo order = null;
        if (succ)
            order = orderService.createOrder(user, goods);
        else
            setSeckillOver(goods.getId());
        return order;
    }

    /**
     * @return 0:排队中 -1:秒杀失败 orderId
     */
    public long getResult(Long userId, long goodsId) {
        SeckillOrder order = orderService.getSkOrderByUserIdAndGoodsId(userId, goodsId);
        if (order != null)
            return order.getOrderId();
        boolean over = getSeckillOver(goodsId);
        return over ? -1 : 0;
    }

    public void setSeckillOver(Long goodsId) {
        redisService.set(SeckillKey.SECKILL_OVER, String.valueOf(goodsId), true);
    }

    public boolean getSeckillOver(long goodsId) {
        return redisService.exist(SeckillKey.SECKILL_OVER, String.valueOf(goodsId));
    }
}
