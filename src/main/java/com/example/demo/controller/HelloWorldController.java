package com.example.demo.controller;

import com.example.demo.dao.UserDao;
import com.example.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
public class HelloWorldController {

    @Value("${com.my.title}")
    private String title;
    @Value("${com.my.name}")
    private String name;
    @Autowired
    private UserDao userDao;

    @RequestMapping("/hello")
    public String index() {
        System.out.println(title);
        System.out.println(name);
        return "hello world, this is fitzx";
    }

    @RequestMapping("/user")
    public User helloUser() {
        User user = new User();
        user.setAge(10);
        user.setName("allen");
        user.setGender(true);
        return user;
    }

    @RequestMapping("/getUser")
    @Cacheable(value="user-key")//zset 名字
    public User getUser(Long id) {
        User user=userDao.findById(id);
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return user;
    }

    @RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
}
