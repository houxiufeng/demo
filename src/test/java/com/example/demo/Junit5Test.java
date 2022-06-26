package com.example.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("junit5功能测试类")
public class Junit5Test {


    @BeforeEach
    void beforeTest() {
        System.out.println("测试就要开始了");

    }
    @AfterEach
    void afterTest() {
        System.out.println("测试结束了");

    }

    @DisplayName("测试displayName注解")
    @Test
    void testDisplayName() {
        System.out.println("hello junit 5");
    }

}
