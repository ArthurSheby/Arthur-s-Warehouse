package com.arthur.controller.replaceEntity;

import com.arthur.entity.Goods;
import com.arthur.entity.Supplier;
import com.arthur.entity.User;
import com.arthur.entity.WareHouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-10-01 20:35
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDetailed {
    private Integer id;
    private String orderNumber;
    private String status;
    private Supplier supplier;
    private User creator = null;
    private User checker;
    private User warehouseman;
    private String createTime;
    private String checkTime;
    private String putTime;
    private WareHouse warehouse;
    private List<Goods> goodsList;
    private List<Integer> goodsNumList;
    private String bottonClass;

    public String getBottonClass() {
        return bottonClass;
    }

    public void setBottonClass(String bottonClass) {
        this.bottonClass = bottonClass;
    }

    public List<Integer> getGoodsNumList() {
        return goodsNumList;
    }

    public void setGoodsNumList(List<Integer> goodsNumList) {
        this.goodsNumList = goodsNumList;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getChecker() {
        return checker;
    }

    public void setChecker(User checker) {
        this.checker = checker;
    }

    public User getWarehouseman() {
        return warehouseman;
    }

    public void setWarehouseman(User warehouseman) {
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

    public WareHouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WareHouse warehouse) {
        this.warehouse = warehouse;
    }
}
