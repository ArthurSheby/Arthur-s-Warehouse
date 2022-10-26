package com.arthur.controller;

import com.arthur.entity.PurchaseGoods;
import com.arthur.service.PurchaseGoodsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-10-01 21:38
 **/
@RestController
@RequestMapping("/purchaseGoods")
public class PurchaseGoodsController {
    @Autowired
    private PurchaseGoodsService purchaseGoodsService;
    @GetMapping("/getAllPurchaseGoods")
    public List<PurchaseGoods> getAllPurchaseGoods(){
        return purchaseGoodsService.list();
    }
    @GetMapping("/getPurchaseGoodsByOrId")
    public List<PurchaseGoods> getPartByOrId(){
        QueryWrapper<PurchaseGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("purchase_order_id","11923020202");
        return purchaseGoodsService.list(queryWrapper);
    }
}
