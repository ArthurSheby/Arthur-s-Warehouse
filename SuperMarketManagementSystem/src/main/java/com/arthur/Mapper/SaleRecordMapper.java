package com.arthur.Mapper;

import com.arthur.entity.SaleRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-10-01 16:46
 **/
@Repository
public interface SaleRecordMapper extends BaseMapper<SaleRecord> {
    @Select("SELECT * from salerecord")
    List<SaleRecord> allSaleRecord();
}
