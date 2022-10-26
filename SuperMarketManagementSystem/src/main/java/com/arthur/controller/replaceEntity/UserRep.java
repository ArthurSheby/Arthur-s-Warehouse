package com.arthur.controller.replaceEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: vueblog
 * @description 接受前端登录请求的用户类
 * @author: Arthur
 * @create: 2022-09-28 20:51
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRep {
    private String phone;
    private String userPassword;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
