package com.example.demo.service.impl;

import com.example.demo.mapper.ESUserRepository;
import com.example.demo.service.ESUserService;
import com.example.demo.vo.ESUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class ESUserServiceImpl implements ESUserService {

    @Autowired
    private ESUserRepository userRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public boolean deleteIndex() {
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(ESUserInfo.class);
        boolean result = indexOperations.delete();
        return result;

    }

    public void createIndex() {
        // 指定文档的数据实体类
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(ESUserInfo.class);
        // 创建索引
        indexOperations.create();
        // 创建字段映射
        Document mapping = indexOperations.createMapping();
        // 给索引设置字段映射
        indexOperations.putMapping(mapping);
    }

    @Override
    public void save(ESUserInfo esUserInfo) {
        esUserInfo.setCreateTime(new Date());
        userRepository.save(esUserInfo);

    }

    @Override
    public void saveAll(List<ESUserInfo> list) {
        userRepository.saveAll(list);

    }

    @Override
    public Iterator<ESUserInfo> findAll() {
        return userRepository.findAll().iterator();
    }

    @Override
    public List<ESUserInfo> findByNameAndAge(String name, Integer age) {
        List<ESUserInfo> userInfos = userRepository.findByNameAndAge(name, age);
        return userInfos;
    }
}
