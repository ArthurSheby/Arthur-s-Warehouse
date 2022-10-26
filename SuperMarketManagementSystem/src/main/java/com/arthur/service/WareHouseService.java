package com.arthur.service;

import com.arthur.Mapper.WareHouseMapper;
import com.arthur.entity.WareHouse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-10-01 10:35
 **/
@Service
public class WareHouseService extends ServiceImpl<WareHouseMapper, WareHouse> {
    public boolean saveWareHouse(WareHouse wareHouse){
        return saveOrUpdate(wareHouse);
    }
}
