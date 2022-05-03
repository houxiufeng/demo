package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserVo {
    private Long id;
    private String name;
    private Integer age;
    private Boolean gender;
    private Date createTime;
}
