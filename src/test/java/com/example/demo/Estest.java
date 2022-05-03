package com.example.demo;

import cn.hutool.json.JSONUtil;
import com.example.demo.mapper.ESUserRepository;
import com.example.demo.vo.ESUserInfo;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Estest {

    @Autowired
    private ESUserRepository esUserRepository;

    @Autowired
    private ElasticsearchRestTemplate esRestTemplate;

    @Test
    public void test1() throws Exception {
        List<ESUserInfo> zuora_user1 = esUserRepository.findByName("zuora_user1");
        zuora_user1.forEach(System.out::println);
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(3);
        System.out.println(localDateTime);
        long l = localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println(l);
    }

    @Test
    public void test2() throws Exception {
        List<ESUserInfo> zuora_user1 = esUserRepository.findByNameAndAge("zuora_user1", 31);
        zuora_user1.forEach(System.out::println);
    }

    @Test
    public void test3() throws Exception {
        List<ESUserInfo> zuora_user1 = esUserRepository.findByNameOrAge("zuora_user1", 31);
        zuora_user1.forEach(System.out::println);
    }

    @Test
    public void test4() throws Exception {
        Pageable pageable =  PageRequest.of(0, 2);
        Page<ESUserInfo> page = esUserRepository.findByName("zuora_user1", pageable);
        System.out.println(JSONUtil.toJsonStr(page));

    }

    @Test
    public void test5() throws Exception {
        Pageable pageable =  PageRequest.of(0, 5);
        Page<ESUserInfo> page = esUserRepository.findByNameNot("zuora_user1", pageable);
        System.out.println(JSONUtil.toJsonStr(page));

    }

    @Test
    public void test6() throws Exception {
        Pageable pageable =  PageRequest.of(0, 50);
        Page<ESUserInfo> page = esUserRepository.findByNameLike("zuora_user", pageable);
        System.out.println(JSONUtil.toJsonStr(page));

    }


    /**
     * GET user_info/_count
     */
    @Test
    public void test7() throws Exception {
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        long count = esRestTemplate.count(searchQueryBuilder.build(), IndexCoordinates.of("user_info"));
        System.out.println("count ->" + count);
    }


    /**
     * GET demo-log/_search
     * {
     *   "query": {
     *     "bool": {
     *       "must": [
     *         {
     *           "term": {
     *             "name": "zuora_user1"
     *           }
     *         },
     *         {
     *           "range": {
     *             "createTime.keyword": {
     *               "gte": "2022-05-01T10:00:00",
     *               "format": "yyyy-MM-dd HH:mm:ss"
     *             }
     *           }
     *         }
     *       ]
     *     }
     *   },
     *   "sort": [
     *     {
     *       "createTime.keyword": {
     *         "order": "desc"
     *       }
     *     }
     *   ],
     *   "size": 20
     * }
     */
    @Test
    public void test8() throws Exception {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        // 查询age=12岁的
        boolQueryBuilder.must().add(new TermQueryBuilder("age", 12));

        // 查询3天内数的数据
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("createTime");
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(3);
        long l = localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        rangeQueryBuilder.gte(l);
        boolQueryBuilder.must().add(rangeQueryBuilder);

        // 分页查询20条
        PageRequest pageRequest = PageRequest.of(0, 20, Sort.by("createTime").descending());

        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        searchQueryBuilder.withQuery(boolQueryBuilder).withPageable(pageRequest);

        Query searchQuery = searchQueryBuilder.build();
        SearchHits<ESUserInfo> hits = esRestTemplate.search(searchQuery, ESUserInfo.class);
        List<SearchHit<ESUserInfo>> hitList = hits.getSearchHits();
        System.out.println("hit size -> " + hitList.size());
        hitList.forEach(hit -> {
            System.out.println("返回数据：" + hit.getContent().toString());
        });
    }


}
