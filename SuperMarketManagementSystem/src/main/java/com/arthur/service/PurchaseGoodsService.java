package com.arthur.service;

import com.arthur.Mapper.PurchaseGoodsMapper;
import com.arthur.entity.PurchaseGoods;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-10-01 21:31
 **/
@Service
public class PurchaseGoodsService extends ServiceImpl<PurchaseGoodsMapper, PurchaseGoods> {
    public boolean savePurchaseGoods(PurchaseGoods purchaseGoods){
        return saveOrUpdate(purchaseGoods);
    }
}
