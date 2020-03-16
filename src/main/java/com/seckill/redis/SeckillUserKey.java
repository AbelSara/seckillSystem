package com.seckill.redis;

/**
 * Created by Albert on 2020/3/8.
 */
public class SeckillUserKey extends BasePrefix {
    private static final int expiretime = 3600 * 24;

    private SeckillUserKey(String prefix) {
        super(prefix);
    }

    private SeckillUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static SeckillUserKey TOKEN = new SeckillUserKey(expiretime, "token");
    public static SeckillUserKey ID = new SeckillUserKey(expiretime, "id");

}
