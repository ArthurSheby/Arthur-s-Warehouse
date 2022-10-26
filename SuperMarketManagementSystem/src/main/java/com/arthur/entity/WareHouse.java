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
 * @create: 2022-10-01 10:25
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName(value ="warehouse")//指定查询的表名
public class WareHouse {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer chargePersonId;
    private String address;
    private String isUse;
    private String picture;
    private int volume;
    private int used;
    private String createTime;

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

    public Integer getChargePersonId() {
        return chargePersonId;
    }

    public void setChargePersonId(Integer chargePersonId) {
        this.chargePersonId = chargePersonId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
