package com.seckill.vo;

import com.seckill.domain.Goods;

import java.util.Date;

/**
 * Created by Albert on 2020/3/10.
 */
public class GoodsVO extends Goods{
    private Integer stockCount;
    private Date startTime;
    private Date endTime;
    private Double seckillPrice;

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(Double seckillPrice) {
        this.seckillPrice = seckillPrice;
    }
}
