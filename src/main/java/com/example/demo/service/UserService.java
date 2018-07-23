package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by fitz on 2018/7/22.
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Cacheable(value="user-key")
    public User getUserById(Long id) {
//        return userDao.getOne(id);
        return userDao.findById(id);
    }
}
