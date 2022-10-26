package com.arthur.service;

import com.arthur.Mapper.SupplierMapper;
import com.arthur.entity.Supplier;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-09-30 21:23
 **/
@Service
public class SupplierService extends ServiceImpl<SupplierMapper, Supplier> {

    public boolean saveSupplier(Supplier supplier) {
        return saveOrUpdate(supplier);
    }
}
