package com.arthur.service;

import com.arthur.Mapper.GoodsMapper;
import com.arthur.entity.Goods;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-09-25 20:29
 **/
@Service
public class GoodsService extends ServiceImpl<GoodsMapper, Goods> {
    public boolean saveGoods(Goods goods) {
        return saveOrUpdate(goods);
    }
}
