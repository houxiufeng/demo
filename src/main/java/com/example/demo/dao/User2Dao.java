package com.example.demo.dao;

import com.example.demo.domain.User;
import com.example.demo.domain.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fitz on 2018/7/28.
 */
@Repository
public interface User2Dao {
    User findByName(String name);
    User findById(Long id);
    User findByNameAndGender(@Param("name") String name, @Param("gender") Boolean gender);
    List<User> findByAge(Integer age);
    void insert(User user);
    void update(User user);
    void delete(Long id);
    List<UserInfo> findUserInfos(@Param("name") String name, @Param("code") String code);
}
