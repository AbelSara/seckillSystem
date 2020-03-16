package com.seckill.service;

import com.seckill.dao.GoodsDao;
import com.seckill.domain.Goods;
import com.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Albert on 2020/3/10.
 */
@Service
public class GoodsService {
    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVO> listGoodsVO() {
        return goodsDao.listGoodsVO();
    }

    public GoodsVO getGoodsVOById(long id) {
        return goodsDao.getById(id);
    }

    public boolean reduceStock(GoodsVO goods) {
        int res = goodsDao.reduceStock(goods.getId());
        return res > 0;
    }
}
