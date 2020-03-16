package com.seckill.redis;

/**
 * Created by Albert on 2020/3/8.
 */
public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();
}
