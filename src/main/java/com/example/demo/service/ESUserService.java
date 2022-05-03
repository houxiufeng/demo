package com.example.demo.service;

import com.example.demo.vo.ESUserInfo;

import java.util.Iterator;
import java.util.List;

public interface ESUserService {

    boolean deleteIndex();

    void createIndex();

    void save(ESUserInfo docBean);

    void saveAll(List<ESUserInfo> list);

    Iterator<ESUserInfo> findAll();

    List<ESUserInfo> findByNameAndAge(String name, Integer age);
}
