package com.seckill.redis;

/**
 * Created by Albert on 2020/3/13.
 */
public class SeckillOrderKey extends BasePrefix{
    private static final int expiretime = 60;

    private SeckillOrderKey(String prefix) {
        super(prefix);
    }

    private SeckillOrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static SeckillOrderKey USER_GOODS = new SeckillOrderKey(expiretime, "user_goods");
}
