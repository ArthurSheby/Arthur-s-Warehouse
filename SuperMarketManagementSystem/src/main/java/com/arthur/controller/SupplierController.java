package com.arthur.controller;

import com.arthur.entity.Goods;
import com.arthur.entity.Supplier;
import com.arthur.entity.User;
import com.arthur.service.GoodsService;
import com.arthur.service.SupplierService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-09-30 21:16
 **/
@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private GoodsService goodsService;

    //获取部芬数据
    @GetMapping("/getPartSupplier")
    public IPage<Supplier> getPartSupplier(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String address){
        IPage<Supplier> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Supplier> queryWrapper = new QueryWrapper<>();
        if (!"null".equals(name)){
            queryWrapper.like("name",name);
        }
        if (!"null".equals(address)){
            queryWrapper.like("address",address);
        }
        queryWrapper.orderByDesc("id");
        //获取每一个supplier的Goods列表
        IPage<Supplier> supplierIPage = supplierService.page(page,queryWrapper);
        List<Supplier> supplierList = supplierIPage.getRecords();
//        List<Goods> goodsList = new ArrayList<>();
        QueryWrapper<Goods> goodsQueryWrapper = null;
        for (Supplier supplier :supplierList){
            goodsQueryWrapper = new QueryWrapper<>();
            goodsQueryWrapper.eq("supper_id",supplier.getId());
            supplier.setListOfGoods(goodsService.list(goodsQueryWrapper));
//            System.out.println("xxxxxxxxxxx"+supplier.toString());
//            System.out.println("###########"+goodsService.list(goodsQueryWrapper));
        }
        supplierIPage.setRecords(supplierList);
        return supplierIPage;
    }

    //插入或更新
    @PostMapping("/insterOrUpdataSupplier")
    public boolean saveOrOpdataSupplier(@RequestBody Supplier supplier){
        return supplierService.saveSupplier(supplier);
    }

    @PostMapping("/deleteManySupplier")
    public boolean deleteManySupplier(@RequestBody List<Integer> idList){
        return supplierService.removeByIds(idList);
     }

    @DeleteMapping("/deleteSupplier/{id}")
    public boolean deleteSupplier(@PathVariable Integer id){
        return supplierService.removeById(id);
    }

    @PostMapping("/insertSupplier")
    public boolean saveSupplier(@RequestBody Supplier supplier){
        //新增或者更新都在
        return supplierService.saveOrUpdate(supplier);
    }
}
