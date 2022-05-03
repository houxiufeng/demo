package com.example.demo.vo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;

@Data
@Document(indexName = "user_info")
@Setting(shards = 1, replicas = 1)
public class ESUserInfo {
    @Id
    private Long id;
    @Field(name = "name", type = FieldType.Text)
    private String name;
    @Field(name = "age", type = FieldType.Keyword)
    private Integer age;
    @Field(name = "gender")
    private Boolean gender;
    /**
     * DateFormat[] format() default { DateFormat.date_optional_time, DateFormat.epoch_millis };
     * date_optional_time 此格式为ISO8601标准 示例：2018-08-31T14:56:18.000+08:00
     * epoch_millis 也就是时间戳 示例1515150699465, 1515150699
     */
    @Field(name = "createTime")
    private Date createTime;

}
