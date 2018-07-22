package com.example.demo.controller;

import com.example.demo.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @Value("${com.my.title}")
    private String title;
    @Value("${com.my.name}")
    private String name;

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
}
