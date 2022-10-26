package com.arthur.Mapper;

import com.arthur.entity.User;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @program: vueblog
 * @description
 * @author: Arthur
 * @create: 2022-09-25 17:37
 **/
//@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    @Select("Select * from `user` where userName LIKE #{userName} limit #{pageNum},#{pageSize}")
    List<User> selectPartUser(@Param("pageNum") Integer pageNum, @Param("pageSize")Integer pageSize,@Param("userName") String userName);

//    @Select("Select * from `user` where userName LIKE #{userName} limit #{pageNum},#{pageSize}")
//    List<User> searchUser(@Param("pageNum") Integer pageNum, @Param("pageSize")Integer pageSize,@Param("userName") String userName);

    @Select("Select * from user")
    List<User> allUser();

    @Insert("INSERT INTO user(id,userName,userPassword,gender,birthday,phone,email,role,createTime)" +
            "VALUES (#{id},#{userName},#{userPassword},#{gender},#{birthday},#{phone},#{email},#{role},#{createTime});")
    int insertUser(User user);

    int update(User user);

    @Delete("delete from user where id = #{id}")
    int delete(@Param("id") Integer id);

    @Select("Select count(*) from user where userName LIKE #{userName}")
    int dataTotalNumber(@Param("userName") String  userName);
}
