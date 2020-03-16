package com.seckill.redis;

/**
 * Created by Albert on 2020/3/14.
 */
public class SeckillKey extends BasePrefix {

    private SeckillKey(String prefix) {
        super(prefix);
    }


    public static SeckillKey SECKILL_OVER = new SeckillKey("over");
}
