package com.seckill.redis;


/**
 * Created by Albert on 2020/3/8.
 */
public class OrderKey extends BasePrefix {
    private OrderKey(String prefix) {
        super(prefix);
    }

    private OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
