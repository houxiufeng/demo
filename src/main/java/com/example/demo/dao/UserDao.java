package com.example.demo.dao;

import com.example.demo.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
//@repository 其实也可以不加，不影响执行，但是idea会有个错误提示，看着心烦。
@Repository
public interface UserDao{
    @Select("select * from user where name = #{name} limit 1")
    User findByName(String name);
    @Select("select * from user where id = #{id}")
    User findById(Long id);
    @Select("select * from user where name = #{name} and gender = #{gender} limit 1")
    User findByNameAndGender(@Param("name") String name, @Param("gender") Boolean gender);
    @Select("select * from user where age = #{age}")
    List<User> findByAge(Integer age);
    @Insert("insert into user(name, age, gender, nick_name) values(#{name},#{age},#{gender},#{nickName})")
    void insert(User user);
//    @Update("update user set nick_name=#{nickName}, name=#{name}, age=#{age}, gender=#{gender} where id =#{id}")
    @Update({"<script>",
            "update user",
            "<set>",
            "<if test='nickName != null and nickName != \"\"'>nick_name=#{nickName},</if>",
            "<if test='name != null'>name=#{name},</if>",
            "<if test='age != null'>age=#{age},</if>",
            "<if test='gender != null'>gender=#{gender},</if>",
            "</set>",
            "where id = #{id}",
            "</script>"
    })
    void update(User user);
    @Delete("delete from user where id = #{id}")
    void delete(Long id);

}
