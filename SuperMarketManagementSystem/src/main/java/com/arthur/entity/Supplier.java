package com.arthur.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-09-30 21:17
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName(value ="supplier")//指定查询的表名
public class Supplier {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "name")
    private String name;
    private String information;
    private String manager;
    private String phone;
    private String address;
    private String createTime;

    //新加属性
    @TableField(exist = false)
    private List<Goods> listOfGoods = null;

    public List<Goods> getListOfGoods() {
        return listOfGoods;
    }

    public void setListOfGoods(List<Goods> listOfGoods) {
        this.listOfGoods = listOfGoods;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String desc) {
        this.information = desc;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
