package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dto.UserDto;
import com.example.demo.po.User;

import java.util.List;

public interface UserService {
    List<User> listByCondition(UserDto userDto);
    Page<User> listByPage(UserDto userDto);
    User addUser(UserDto userDto);
    void saveUser(User user);
    void deleteUser(Long id);
    void removeUser(Long id);
    void updateUser(UserDto userDto);
    void modifyUser(User user);

}
