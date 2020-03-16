package com.seckill.redis;

/**
 * Created by Albert on 2020/3/8.
 */
public abstract class BasePrefix implements KeyPrefix {

    private int expireSeconds;
    private String prefix;

    /**
     * @param prefix
     */
    public BasePrefix(String prefix) {
        this(0, prefix);
    }

    /**
     * @param expireSeconds 0 表示永不过期
     * @param prefix
     */
    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
