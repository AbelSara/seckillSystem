package com.seckill.dao;

import com.seckill.domain.OrderInfo;
import com.seckill.domain.SeckillOrder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * Created by Albert on 2020/3/10.
 */
@Component
@Mapper
public interface OrderDao {
    @Select("select * from seckill_order where user_id = #{userId} and goods_id = #{goodsId}")
    SeckillOrder getSkOrderByUserIdAndGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    @Insert("insert into order_info (user_id, goods_id, delivery_addr_id, goods_name, goods_count, " +
            "goods_price, order_channel, status, create_date)values(#{userId}, #{goodsId}, #{deliveryAddrId}," +
            "#{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel}, #{status}, #{createDate})")
    @SelectKey(keyColumn = "id", keyProperty = "id",
            resultType = long.class, before = false, statement = "select last_insert_id()")
    long insert(OrderInfo orderInfo);

    @Select("select * from order_info where id = #{orderId}")
    OrderInfo getOrderById(@Param("orderId") long orderId);
}
