package com.seckill.dao;

import com.seckill.domain.Goods;
import com.seckill.vo.GoodsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Albert on 2020/3/10.
 */
@Component
@Mapper
public interface GoodsDao {
    @Select("select g.*,sg.stock_count,sg.start_time,sg.end_time " +
            "from seckill_goods sg left join goods g on sg.goods_id=g.id")
    List<GoodsVO> listGoodsVO();

    @Select("select g.*,sg.stock_count,sg.start_time,sg.end_time,sg.seckill_price " +
            "from seckill_goods sg left join goods g on sg.goods_id=g.id where g.id = #{goodsId}")
    GoodsVO getById(@Param("goodsId") long goodsId);

    @Update("update seckill_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
    int reduceStock(@Param("goodsId") long goodsId);
}
