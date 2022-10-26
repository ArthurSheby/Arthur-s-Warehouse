package com.arthur.controller;

import com.arthur.entity.Goods;
import com.arthur.service.GoodsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-09-29 14:10
 **/
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    //获取所有goods
    @GetMapping("/getAllGoods")
    public List<Goods> getAllGoods(){
        return goodsService.list();
    }
    //分页查询货物
    @GetMapping("/getPartGoods")
    public IPage<Goods> getPartGoods(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String goodsName,
            @RequestParam(defaultValue = "") String firstClass){
        IPage<Goods> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        if (!"null".equals(goodsName)){
            queryWrapper.like("goods_name",goodsName);
        }
        if (!"null".equals(firstClass)){
            queryWrapper.like("first_class",firstClass);
        }
        queryWrapper.orderByDesc("id");
        return goodsService.page(page,queryWrapper);
    }
    //插入商品
    @PostMapping("/insertGoods")
    public boolean save(@RequestBody Goods goods){
        return goodsService.saveGoods(goods);
    }
    //批量删除商品
    @PostMapping("/deleteManyGoods")
    public boolean deleteManyGoods(@RequestBody List<Integer> idList){
        return goodsService.removeByIds(idList);
    }
    //保存修改，或者添加货品
    @PostMapping("/saveOrInsertGoods")
    public boolean saveOrInsertGoods(@RequestBody Goods goods){
        return goodsService.saveGoods(goods);
    }
    //通过id删除商品
    @DeleteMapping("/deleteGoods/{id}")
    public boolean deleteGoodsById(@PathVariable Integer id){
        return goodsService.removeById(id);
    }

    //模糊查询名称获得商品
    @GetMapping("/getGoodsByQueryGoodsName")
    public List<Goods> getGoodsByQueryGoodsName(@RequestParam String queryName){
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("goods_name",queryName);
        return goodsService.list(queryWrapper);
    }
}
