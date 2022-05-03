package com.example.demo.mapper;

import com.example.demo.vo.ESUserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

//泛型的参数分别是实体类型和主键类型
public interface ESUserRepository extends ElasticsearchRepository<ESUserInfo, Long> {

    /**
     * 精确查找
     * 方法名规则：finByxxx
     *
     * @param name
     * @return 员工数据集
     */
    List<ESUserInfo> findByName(String name);

    /**
     * AND 语句查询
     *
     * @param name
     * @param age
     * @return 员工数据集
     */
    List<ESUserInfo> findByNameAndAge(String name, Integer age);

    /**
     * OR 语句查询
     *
     * @param name
     * @param age
     * @return 员工数据集
     */
    List<ESUserInfo> findByNameOrAge(String name, Integer age);

    /**
     * 分页查询员工信息
     *
     * @param name
     * @param page
     * @return 员工数据集
     * 注：等同于下面代码 @Query("{\"bool\" : {\"must\" : {\"term\" : {\"name\" : \"?0\"}}}}")
     */
    Page<ESUserInfo> findByName(String name, Pageable page);

    /**
     * NOT 语句查询
     *
     * @param name
     * @param page
     * @return 员工数据集
     */
    Page<ESUserInfo> findByNameNot(String name, Pageable page);

    /**
     * LIKE 语句查询
     *
     * @param name
     * @param page
     * @return 员工数据集
     */
    Page<ESUserInfo> findByNameLike(String name, Pageable page);
}
