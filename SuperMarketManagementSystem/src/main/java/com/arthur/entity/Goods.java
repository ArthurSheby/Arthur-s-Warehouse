package com.arthur.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;


/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-09-29 13:55
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName(value ="goods")//指定查询的表名
public class Goods {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "goods_name")//指定对应书库库的字段名称
    private String goodsName;
    private Double innerPrice;
    private Double salePrice;
    private Integer supperId;
    private String firstClass;
    private String secondClass;
    private String thirdClass;
    private Integer alterNumber;
    private String information;
    private String createTime;
    private String picture;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createtime) {
        this.createTime = createtime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getInnerPrice() {
        return innerPrice;
    }

    public void setInnerPrice(Double innerPrice) {
        this.innerPrice = innerPrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getSupperId() {
        return supperId;
    }

    public void setSupperId(Integer supperId) {
        this.supperId = supperId;
    }

    public String getFirstClass() {
        return firstClass;
    }

    public void setFirstClass(String firstClass) {
        this.firstClass = firstClass;
    }

    public String getSecondClass() {
        return secondClass;
    }

    public void setSecondClass(String secondClass) {
        this.secondClass = secondClass;
    }

    public String getThirdClass() {
        return thirdClass;
    }

    public void setThirdClass(String thirdClass) {
        this.thirdClass = thirdClass;
    }

    public Integer getAlterNumber() {
        return alterNumber;
    }

    public void setAlterNumber(Integer alterNumber) {
        this.alterNumber = alterNumber;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
