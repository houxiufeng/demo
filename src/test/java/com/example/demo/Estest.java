package com.example.demo;

import cn.hutool.json.JSONUtil;
import com.example.demo.common.backoff.BackOff;
import com.example.demo.common.backoff.ExponentialBackOff;
import com.example.demo.common.backoff.FixedBackOff;
import com.example.demo.common.backoff.Retry;
import com.example.demo.mapper.ESUserRepository;
import com.example.demo.vo.ESUserInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
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

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Estest {

    @Autowired
    private ESUserRepository esUserRepository;

    @Autowired
    private ElasticsearchRestTemplate esRestTemplate;

    @Autowired
    private RestHighLevelClient elasticsearchClient;

//    private BackOff backOff = ExponentialBackOff.getDefault();
    private BackOff backOff = new FixedBackOff(1000L, 5000L);

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

    @Test
    public void test9() throws Exception {
        Map<String, Object> doc1 = Maps.newHashMap();
        doc1.put("name","x1");
        doc1.put("age",11);
        Map<String, Object> doc2 = Maps.newHashMap();
        doc2.put("name","x2");
        doc2.put("age",12);
        Map<String, Object> doc3 = Maps.newHashMap();
        doc3.put("name","x3");
        doc3.put("age","xxad");
        IndexRequest indexRequest1 = new IndexRequest("user_info").id("5").source(doc1);
        IndexRequest indexRequest2 = new IndexRequest("user_info").id("6").source(doc2);
        IndexRequest indexRequest3 = new IndexRequest("user_info").id("7").source(doc3);
        BulkRequest request = new BulkRequest();
        request.add(indexRequest1);
        request.add(indexRequest2);
        request.add(indexRequest3);
//        List<String> ids = Lists.newArrayList("1","3","5");
//        request.requests().removeIf(x -> !ids.contains(x.id()));
//        System.out.println(request);

        BulkResponse response = elasticsearchClient.bulk(request, RequestOptions.DEFAULT);

        List<String> failedIds = Arrays.stream(response.getItems()).filter(BulkItemResponse::isFailed).map(x -> x.getId()).collect(Collectors.toList());

        BulkResponse bulkItemResponses = retryFailure(failedIds, request);

        System.out.println(bulkItemResponses);
    }

    private BulkResponse retryFailure(List<String> failedIds, BulkRequest bulkRequest) throws IOException {
        bulkRequest.requests().removeIf(x -> !failedIds.contains(x.id()));
        BulkResponse response = elasticsearchClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return response;
    }

    @Test
    public void test10() {
        Map<String, Object> doc1 = Maps.newHashMap();
        doc1.put("name","x1");
        doc1.put("age",1111);
        Map<String, Object> doc2 = Maps.newHashMap();
        doc2.put("name","x2");
        doc2.put("age",1222);
        Map<String, Object> doc3 = Maps.newHashMap();
        doc3.put("name","x3");
        doc3.put("age",333);
        IndexRequest indexRequest1 = new IndexRequest("user_info").id("5").source(doc1);
        IndexRequest indexRequest2 = new IndexRequest("user_info").id("6").source(doc2);
        IndexRequest indexRequest3 = new IndexRequest("user_info").id("7").source(doc3);
        BulkRequest request = new BulkRequest();
        request.add(indexRequest1);
        request.add(indexRequest2);
        request.add(indexRequest3);
        BulkResponse bulkItemResponses = retryBulkInsert(request);
        System.out.println(bulkItemResponses.getItems().length);

    }
    private BulkResponse retryBulkInsert(BulkRequest toBeSubmitted) {
        AtomicInteger retry = new AtomicInteger(0);
        Predicate<BulkResponse> predicate = bulkResponse -> {
            System.out.println("-------retry:" + retry.get());
            boolean b = Objects.nonNull(bulkResponse);
            if(!b) {
                return false;
            }
            List<String> failedIds = Arrays.stream(bulkResponse.getItems())
                    .filter(BulkItemResponse::isFailed)
                    .map(x -> x.getId())
                    .collect(Collectors.toList());
            if (failedIds.size() > 0 && retry.get() < 3) {
                retry.incrementAndGet();
                toBeSubmitted.requests().removeIf(x -> !failedIds.contains(x.id()));
                return false;
            }
            return true;
        };

        return Retry.untilSuccess(backOff, TimeUnit.MILLISECONDS,() -> elasticsearchClient.bulk(toBeSubmitted, RequestOptions.DEFAULT), predicate);
    }


}
