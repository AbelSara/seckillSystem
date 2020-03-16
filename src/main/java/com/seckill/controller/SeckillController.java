package com.seckill.controller;

import com.seckill.domain.OrderInfo;
import com.seckill.domain.SeckillOrder;
import com.seckill.domain.SeckillUser;
import com.seckill.rabbitmq.MQSender;
import com.seckill.rabbitmq.SeckillMessage;
import com.seckill.redis.GoodsKey;
import com.seckill.redis.RedisService;
import com.seckill.result.CodeMsg;
import com.seckill.result.Result;
import com.seckill.service.GoodsService;
import com.seckill.service.OrderService;
import com.seckill.service.SeckillService;
import com.seckill.vo.GoodsVO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Albert on 2020/3/10.
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Autowired
    SeckillService seckillService;

    @Autowired
    MQSender sender;

    Map<Long, Boolean> overMap = new HashMap<>();

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> getResult(Model model, SeckillUser user, @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null)
            return Result.error(CodeMsg.SESSION_ERROR);
        Long orderId = seckillService.getResult(user.getId(), goodsId);
        return Result.success(orderId);
    }

    @RequestMapping(value = "/do_seckill", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> seckill(Model model, SeckillUser user, @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null)
            return Result.error(CodeMsg.SESSION_ERROR);
        if (overMap.get(goodsId))
            return Result.error(CodeMsg.SECKILL_EMPTY);
        long stock = redisService.decr(GoodsKey.goodsSeckillStock, String.valueOf(goodsId));
        if (stock < 0) {
            overMap.put(goodsId, true);
            return Result.error(CodeMsg.SECKILL_EMPTY);
        }
        SeckillOrder order = orderService.getSkOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if (order != null)
            return Result.error(CodeMsg.SECKILL_DUPLICATED);
        SeckillMessage message = new SeckillMessage(user, goodsId);
        sender.sendSeckillMessage(message);
        return Result.success(0);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVO> goodsList = goodsService.listGoodsVO();
        if (goodsList == null)
            return;
        for (GoodsVO goods : goodsList) {
            overMap.put(goods.getId(), false);
            redisService.set(GoodsKey.goodsSeckillStock, String.valueOf(goods.getId()), goods.getStockCount());
        }
    }
}