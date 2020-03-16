package com.seckill.rabbitmq;

import com.seckill.domain.SeckillUser;

import java.io.Serializable;

/**
 * Created by Albert on 2020/3/14.
 */
public class SeckillMessage implements Serializable{
    private SeckillUser user;
    private long goodsId;

    public SeckillMessage(SeckillUser user, long goodsId) {
        this.user = user;
        this.goodsId = goodsId;
    }

    public SeckillUser getUser() {
        return user;
    }

    public void setUser(SeckillUser user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
