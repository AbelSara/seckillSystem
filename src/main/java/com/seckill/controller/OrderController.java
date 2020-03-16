package com.seckill.controller;

import com.seckill.domain.OrderInfo;
import com.seckill.domain.SeckillUser;
import com.seckill.result.CodeMsg;
import com.seckill.result.Result;
import com.seckill.service.GoodsService;
import com.seckill.service.OrderService;
import com.seckill.vo.GoodsVO;
import com.seckill.vo.OrderDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Albert on 2020/3/13.
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVO> orderInfo(Model model, SeckillUser user, @RequestParam("orderId") long orderId) {
        if (user == null)
            return Result.error(CodeMsg.SESSION_ERROR);
        model.addAttribute("user", user);
        OrderInfo order = orderService.getOrderById(orderId);
        if (order == null)
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        GoodsVO goods = goodsService.getGoodsVOById(order.getGoodsId());
        OrderDetailVO orderDetail = new OrderDetailVO(order, goods);
        return Result.success(orderDetail);
    }
}
