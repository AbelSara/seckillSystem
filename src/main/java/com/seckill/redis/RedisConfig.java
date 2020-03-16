package com.seckill.redis;

import com.sun.xml.internal.ws.api.FeatureListValidatorAnnotation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Albert on 2020/3/8.
 */
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfig {
    private String host;
    private int port;
    private int timeout;
    private String password;
    @Value("${spring.redis.lettuce.pool.max-active}")
    private int lettucePoolMaxActive;
    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int lettucePoolMaxIdle;
    @Value("${spring.redis.lettuce.pool.max-wait}")
    private int lettucePoolMaxWait;
    @Value("${spring.redis.lettuce.pool.min-idle}")
    private int lettucePoolMinIdle;

    public int getLettucePoolMinIdle() {
        return lettucePoolMinIdle;
    }

    public void setLettucePoolMinIdle(int lettucePoolMinIdle) {
        this.lettucePoolMinIdle = lettucePoolMinIdle;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLettucePoolMaxActive() {
        return lettucePoolMaxActive;
    }

    public void setLettucePoolMaxActive(int lettucePoolMaxActive) {
        this.lettucePoolMaxActive = lettucePoolMaxActive;
    }

    public int getLettucePoolMaxIdle() {
        return lettucePoolMaxIdle;
    }

    public void setLettucePoolMaxIdle(int lettucePoolMaxIdle) {
        this.lettucePoolMaxIdle = lettucePoolMaxIdle;
    }

    public int getLettucePoolMaxWait() {
        return lettucePoolMaxWait;
    }

    public void setLettucePoolMaxWait(int lettucePoolMaxWait) {
        this.lettucePoolMaxWait = lettucePoolMaxWait;
    }
}
