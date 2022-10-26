package com.arthur.Mapper;

import com.arthur.entity.WareHouse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-10-01 10:32
 **/

@Repository
public interface WareHouseMapper extends BaseMapper<WareHouse> {
    @Select("SELECT * FROM warehouse")
    List<WareHouse> allWareHouse();
}
