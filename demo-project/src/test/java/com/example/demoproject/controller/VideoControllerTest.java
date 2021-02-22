package com.example.demoproject.controller;

import com.example.demoproject.DemoProjectApplication;
import com.example.demoproject.mapper.VideoMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoProjectApplication.class})
@AutoConfigureMockMvc
class VideoControllerTest {


    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    public MockMvc mockMvc;

    @Test
    void list() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/pub/video/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        System.out.println("status: " + status);

        String result = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("result: " + result);
    }
}