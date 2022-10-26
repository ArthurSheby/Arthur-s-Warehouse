package com.arthur.controller;

import com.arthur.controller.replaceEntity.UserRep;
import com.arthur.entity.User;
import com.arthur.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-09-25 18:05
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

//    对数据库进行插入请求
    @PostMapping("/insertUser")
    public boolean save(@RequestBody User user){
        //新增或者更新都在
        return userService.saveUser(user);
    }

    @GetMapping("/getAllUser")
    public List<User> getAllUser(){
        return userService.list();
    }

    @DeleteMapping("/deleteUser/{id}")
    public boolean delete(@PathVariable Integer id){
        return userService.removeById(id);
    }

    @PostMapping("/deleteManyUser")
    public boolean deleteMany(@RequestBody List<Integer> idList){
        return userService.removeByIds(idList);
    }

    //分页查询：接口路径：/user/getPartUser?pageNum=1&pageSize=10
    //参数使用@RequestParam接收
    //limit 第一个参数 = （pageNum-1）*pageSize
    //第二个参数：pageSize
//    @GetMapping("/getPartUser")
//    public Map<String,Object> getPartUser(@RequestParam Integer pageNum,@RequestParam Integer pageSize,@RequestParam String userName){
//        Map<String,Object> result = new HashMap<>();
//        result.put("pageNum",pageNum);
//        pageNum = (pageNum-1)*pageSize;
//        userName = "%"+userName+"%";
//        result.put("dataList",userMapper.selectPartUser(pageNum,pageSize,userName));
//        int datatTotalNumber = userMapper.dataTotalNumber(userName);
//        result.put("totalNumber",datatTotalNumber);
//        return result;
//    }
    @GetMapping("/getPartUser")
    public IPage<User> getPartUser(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String userName,
            @RequestParam(defaultValue = "") String address){
        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!"null".equals(userName)){
            queryWrapper.like("userName",userName);
        }
        if (!"null".equals(address)){
            queryWrapper.like("address",address);
        }
        queryWrapper.orderByDesc("id");
        return userService.page(page,queryWrapper);
    }

    @PostMapping("/login")
//    @RequestBody将前端传过来的json，转化为java对象
    public Map<String,Object> login(@RequestBody UserRep user){
        String phone = user.getPhone();
        String password = user.getUserPassword();
        System.out.println("phone  "+phone+"  password   "+password);
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isBlank(phone)||StringUtils.isBlank(password)){
            map.put("isLogin",false);
        }else if (userService.login(user)!=null){
            map.put("isLogin",true);
            map.put("user",userService.login(user));
        }
        return map;
    }
}
