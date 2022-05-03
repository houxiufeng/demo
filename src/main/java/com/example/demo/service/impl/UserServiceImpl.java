package com.example.demo.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.SpringContextUti;
import com.example.demo.dto.UserDto;
import com.example.demo.event.UserEvent;
import com.example.demo.mapper.UserMapper;
import com.example.demo.po.User;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> listByCondition(UserDto userDto) {
        User user = Convert.convert(User.class, userDto);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>(user);
        List<User> users = userMapper.selectList(userQueryWrapper);

        logger.info("userService listUser:{}", users);
        return users;
    }

    @Override
    public Page<User> listByPage(UserDto userDto) {
        Page<User> page = new Page<>(userDto.getPageNum(), userDto.getPageSize());
        User user = Convert.convert(User.class, userDto);
        Page<User> userPage = userMapper.selectPage(page, new QueryWrapper<>(user));
        return userPage;
    }

    @Override
    public User addUser(UserDto userDto) {
        User user = Convert.convert(User.class, userDto);
        saveUser(user);
        logger.info("userService addUser:{}", user);
        SpringContextUti.publish(new UserEvent(this, user, "add"));
        return user;
    }

    @Override
    public void saveUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userMapper.selectById(id);
        if (user != null) {
            removeUser(user.getId());
        } else {
            user = new User();
            user.setId(id);
        }
        SpringContextUti.publish(new UserEvent(this, user, "delete"));

    }

    @Override
    public void removeUser(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public void updateUser(UserDto userDto) {
        User user = Convert.convert(User.class, userDto);
        modifyUser(user);
        SpringContextUti.publish(new UserEvent(this, user, "update"));

    }

    @Override
    public void modifyUser(User user) {
        userMapper.updateById(user);
    }
}
