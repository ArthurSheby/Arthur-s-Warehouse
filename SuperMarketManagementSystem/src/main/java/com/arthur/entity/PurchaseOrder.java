package com.arthur.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-10-01 20:11
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName(value ="purchaseorder")//指定查询的表名
public class PurchaseOrder {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String orderNumber;
    private Integer status;
    private Integer supplierId;
    private Integer creatorId;
    private Integer checkerId;
    @TableField(value = "warehouseman")//指定对应书库库的字段名称
    private Integer warehouseman;
    private String createTime;
    private String checkTime;
    private String putTime;
    private Integer warehouseId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(Integer checkerId) {
        this.checkerId = checkerId;
    }

    public Integer getWarehouseman() {
        return warehouseman;
    }

    public void setWarehouseman(Integer warehouseman) {
        this.warehouseman = warehouseman;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getPutTime() {
        return putTime;
    }

    public void setPutTime(String putTime) {
        this.putTime = putTime;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }
}
