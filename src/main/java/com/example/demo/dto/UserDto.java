package com.example.demo.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private Integer age;
    private Boolean gender;
    private Integer pageNum;
    private Integer pageSize;
}
