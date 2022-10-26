package com.arthur.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-10-01 21:22
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName(value ="purchasegoods")//指定查询的表名
public class PurchaseGoods {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String purchaseOrderId;
    private int goodsId;
    private Integer goodsNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }
}
