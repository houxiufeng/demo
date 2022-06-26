package com.example.demo;

import cn.hutool.json.JSONUtil;
import com.example.demo.common.RedisUtil;
import com.example.demo.mapper.ESUserRepository;
import com.example.demo.mapper.UserMapper;
import com.example.demo.po.User;
import com.example.demo.service.ESUserService;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

@WebMvcTest
@Slf4j
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    /**
     * 我们可以使用@mockBean注解将Mock对象添加到Spring上下文中。
     * Mock将替换Spring上下文中任何相同类型的现有bean，如果没有定义相同类型的bean，将添加一个新的bean
     */
    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;
    @MockBean
    private ESUserService esUserService;
    @MockBean
    private RedisUtil redisUtil;
    @MockBean
    private ESUserRepository userRepository;


    @Test
    void TestInsertUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("test");
        user.setAge(0);
        user.setGender(false);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());

        String userStr = JSONUtil.toJsonStr(user);

        Mockito.when(userMapper.insert(user)).thenReturn(1);
        Mockito.when(userService.addUser(ArgumentMatchers.any())).thenReturn(user);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .request(HttpMethod.POST, "/resource/user")
                                .contentType("application/json")
                                .content(userStr)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gender").value(false))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString());


    }
}
