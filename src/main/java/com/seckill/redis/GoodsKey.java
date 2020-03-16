package com.seckill.redis;

/**
 * Created by Albert on 2020/3/8.
 */
public class GoodsKey extends BasePrefix {
    private GoodsKey(String prefix) {
        super(prefix);
    }

    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKey goodsList = new GoodsKey(60, "goodsList");
    public static GoodsKey goodsDetail = new GoodsKey(60, "goodsDetail");
    public static GoodsKey goodsSeckillStock = new GoodsKey(0, "goodsSeckillStock");

}
