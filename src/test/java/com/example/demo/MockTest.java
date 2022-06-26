package com.example.demo;

import com.example.demo.controller.UserController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@Slf4j
public class MockTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController()).build();

    }

    @Test
    public void saveUser() throws Exception {
        String userStr = "{\"id\":3,\"name\":\"fitz\",\"age\":20,\"gender\":true}";
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .request(HttpMethod.POST, "/resource/mock/user")
                        .contentType("application/json")
                        .content(userStr)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("fitz"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gender").value(true))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString());
    }
}
