package com.seckill.vo;

import com.seckill.domain.SeckillUser;

/**
 * Created by Albert on 2020/3/13.
 */
public class GoodsDetailVO {
    SeckillUser user;
    GoodsVO goods;
    // >0-等待时间 <0-已结束 =0-正在秒杀
    int seckillStatus;
    // 0-倒计时 1-正在秒杀 2-已结束
    int remainSeconds = 0;

    public GoodsDetailVO(SeckillUser user, GoodsVO goods, int seckillStatus, int remainSeconds) {
        this.user = user;
        this.goods = goods;
        this.seckillStatus = seckillStatus;
        this.remainSeconds = remainSeconds;
    }

    public SeckillUser getUser() {
        return user;
    }

    public void setUser(SeckillUser user) {
        this.user = user;
    }

    public GoodsVO getGoods() {
        return goods;
    }

    public void setGoods(GoodsVO goods) {
        this.goods = goods;
    }

    public int getSeckillStatus() {
        return seckillStatus;
    }

    public void setSeckillStatus(int seckillStatus) {
        this.seckillStatus = seckillStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    @Override
    public String toString() {
        return "GoodsDetailVO{" +
                "user=" + user +
                ", goods=" + goods +
                ", seckillStatus=" + seckillStatus +
                ", remainSeconds=" + remainSeconds +
                '}';
    }
}
