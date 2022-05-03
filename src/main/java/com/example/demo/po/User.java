package com.example.demo.po;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private Boolean gender;
    private Date createTime;
    private Date updateTime;
}
