package com.arthur.service;

import com.arthur.Mapper.PurchaseOrderMapper;
import com.arthur.entity.PurchaseOrder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-10-01 20:23
 **/
@Service
public class PurchaseOrderService extends ServiceImpl<PurchaseOrderMapper, PurchaseOrder> {
    public boolean saveUser(PurchaseOrder purchaseOrder){
        return saveOrUpdate(purchaseOrder);
    }
}
