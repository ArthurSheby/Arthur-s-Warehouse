package com.arthur.service;

import com.arthur.Mapper.UserMapper;
import com.arthur.controller.replaceEntity.UserRep;
import com.arthur.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-09-25 20:29
 **/
@Service
public class UserService extends ServiceImpl<UserMapper,User> {
    public boolean saveUser(User user){
        return saveOrUpdate(user);
    }

    public User login(UserRep user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",user.getPhone());
        queryWrapper.eq("userPassword",user.getUserPassword());
//        User one = getOne(queryWrapper);
//            System.out.println(one.toString());
        return getOne(queryWrapper);
    }
//    @Autowired
//    private UserMapper userMapper;
//
//     public int saveUser(User user){
//         if (user.getId() == null){
//             return userMapper.insertUser(user);
//         }else {
//             return userMapper.update(user);
//         }
//     }
}
