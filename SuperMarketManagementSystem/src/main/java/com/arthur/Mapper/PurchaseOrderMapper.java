package com.arthur.Mapper;

import com.arthur.entity.PurchaseOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-10-01 20:19
 **/
@Repository
public interface PurchaseOrderMapper extends BaseMapper<PurchaseOrder> {
    @Select("SELECT * FROM purchaseorder")
    List<PurchaseOrder> allPurchaseOrder();
}
