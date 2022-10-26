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
 * @create: 2022-10-01 16:40
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName(value ="salerecord")//指定查询的表名
public class SaleRecord {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "order_id")
    private String orderId;
    @TableField(value = "seller")
    private String seller;
    @TableField(value = "payment_amount")
    private Double paymentAmount;
    @TableField(value = "change_amount")
    private Double changeAmount;
    @TableField(value = "pay_method")
    private String payMethod;
    @TableField(value = "create_time")
    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Double getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(Double changeAmount) {
        this.changeAmount = changeAmount;
    }

    public String getpayMethod() {
        return payMethod;
    }

    public void setpayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
