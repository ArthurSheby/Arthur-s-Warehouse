package com.arthur.service;

import com.arthur.Mapper.SaleRecordMapper;
import com.arthur.entity.SaleRecord;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-10-01 16:49
 **/
@Service
public class SaleRecordService extends ServiceImpl<SaleRecordMapper, SaleRecord> {
    public boolean saveSaleRecord(SaleRecord saleRecord){
        return saveOrUpdate(saleRecord);
    }
}
