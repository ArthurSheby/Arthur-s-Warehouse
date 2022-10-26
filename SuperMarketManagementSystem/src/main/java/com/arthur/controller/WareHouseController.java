package com.arthur.controller;

import com.arthur.entity.WareHouse;
import com.arthur.service.WareHouseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-10-01 10:40
 **/
@RestController
@RequestMapping("/warehouse")
public class WareHouseController {
    @Autowired
    private WareHouseService wareHouseService;

    //获取warehose中的所有数据
    @GetMapping("/getAllWareHouse")
    public List<WareHouse> getAllWareHouse(){
        return wareHouseService.list();
    }

    @GetMapping("/getPartWareHouse")
    public IPage<WareHouse> getPartWareHouse(@RequestParam Integer pageNum,
                                             @RequestParam Integer pageSize,
                                             @RequestParam(defaultValue = "") String wareHouseName,
                                             @RequestParam(defaultValue = "") String address){
        IPage<WareHouse> page = new Page<>(pageNum,pageSize);
        QueryWrapper<WareHouse> queryWrapper = new QueryWrapper<>();
        if (!"null".equals(wareHouseName)){
            queryWrapper.like("name",wareHouseName);
        }
        if (!"null".equals(address)){
            queryWrapper.like("address",address);
        }
        queryWrapper.orderByDesc("id");
        return wareHouseService.page(page,queryWrapper);
    }

    @PostMapping("/insertWareHouse")
    public boolean insertWareHouse(@RequestBody WareHouse wareHouse){
        if (wareHouse.getIsUse().equals("true")){
            wareHouse.setIsUse("1");
        }
        System.out.println("@@@@@@@@@wareHouse is "+wareHouse.toString());
        return wareHouseService.saveWareHouse(wareHouse);
    }

}
