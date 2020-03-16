package com.seckill.dao;

import com.seckill.domain.SeckillOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * Created by Albert on 2020/3/11.
 */
@Component
@Mapper
public interface SeckillOrderDao {
    @Insert("insert into seckill_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    void insert(SeckillOrder seckillOrder);
}
