package com.seckill.rabbitmq;

import com.seckill.domain.OrderInfo;
import com.seckill.domain.SeckillOrder;
import com.seckill.domain.SeckillUser;
import com.seckill.redis.RedisService;
import com.seckill.result.CodeMsg;
import com.seckill.result.Result;
import com.seckill.service.GoodsService;
import com.seckill.service.OrderService;
import com.seckill.service.SeckillService;
import com.seckill.vo.GoodsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Albert on 2020/3/14.
 */
@Service
public class MQReceiver {
    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    SeckillService seckillService;

    @Autowired
    RedisService redisService;

    @RabbitListener(queues = MQConfig.SECKILL_QUEUE)
    public void receive(String message) {
        log.info("receive message: " + message);
        SeckillMessage msg = RedisService.stringToBean(message, SeckillMessage.class);
        SeckillUser user = msg.getUser();
        long goodsId = msg.getGoodsId();
        GoodsVO goodsVO = goodsService.getGoodsVOById(goodsId);
        int stock = goodsVO.getStockCount();
        if (stock <= 0) {
            return;
        }
        SeckillOrder order = orderService.getSkOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if (order != null) {
            return;
        }
        //秒杀逻辑
        OrderInfo orderInfo = seckillService.secKill(user, goodsVO);
    }
}
