package com.arthur.Mapper;

import com.arthur.entity.PurchaseGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-10-01 21:28
 **/
@Repository
public interface PurchaseGoodsMapper extends BaseMapper<PurchaseGoods> {
    @Select("SELECT * from purchasegoods")
    List<PurchaseGoods> allPurseGoods();
}
