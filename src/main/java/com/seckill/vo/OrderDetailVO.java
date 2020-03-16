package com.seckill.vo;

import com.seckill.domain.OrderInfo;

/**
 * Created by Albert on 2020/3/13.
 */
public class OrderDetailVO {
    OrderInfo order;
    GoodsVO goods;

    public OrderDetailVO(OrderInfo order, GoodsVO goods) {
        this.order = order;
        this.goods = goods;
    }

    public OrderInfo getOrder() {
        return order;
    }

    public void setOrder(OrderInfo order) {
        this.order = order;
    }

    public GoodsVO getGoods() {
        return goods;
    }

    public void setGoods(GoodsVO goods) {
        this.goods = goods;
    }
}
