package com.arthur;
import java.util.List;
import java.util.UUID;

import com.arthur.Mapper.UserMapper;
import com.arthur.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication//标注这个类是一个springboot应用
public class VueblogApplication {
    @Autowired
    private UserMapper userMapper;

    public static void main(String[] args) {
        SpringApplication.run(VueblogApplication.class, args);
        //做了四件事请
//        1.推断应用的类型是普通的项目还是web项目
//        2.查找并加载所有可用初始化器，设置到initillizers属性中
//        3.找出所有的应用程序监听器，设置到listeners属性中
//        4.推断并设置main方法的定义类，找到运行的主要类
        System.out.println("程序开始了");
    }
    @GetMapping("/")
    public String index(){
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr = str.replace("-", "");
        System.out.println(uuidStr);
        return "第一个程序";
    }
    @GetMapping("/shuzu")
    public List<User> showAllUser() {
        return userMapper.allUser();
    }

}
