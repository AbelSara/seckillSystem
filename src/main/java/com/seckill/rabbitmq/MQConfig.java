package com.seckill.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by Albert on 2020/3/14.
 */
@Configuration
public class MQConfig {
    public static final String SECKILL_QUEUE = "seckill_queue";

    /**
     * Direct 交换机Exchange
     * Topic
     */
    @Bean
    public Queue getQueue() {
        return new Queue(SECKILL_QUEUE, true);
    }
}
