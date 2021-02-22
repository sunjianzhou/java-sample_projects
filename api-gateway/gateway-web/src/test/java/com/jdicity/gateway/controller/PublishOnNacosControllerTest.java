package com.jdicity.gateway.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("integrationTest")
@Sql("classpath:sql/schema.sql")
class PublishOnNacosControllerTest {

    @Autowired
    public MockMvc mvc;

    public MockHttpServletRequestBuilder request = null;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        when(restTemplate.postForEntity(anyString(), any(), any()))
                .thenReturn(new ResponseEntity<>("true", HttpStatus.OK));
    }

    @Test
    void publishGateway() throws Exception {
        request = MockMvcRequestBuilders.post("/publish/gateway")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assert(responseContent.contains("发布路由信息成功"));
    }

    @Test
    void publishFlowRule() throws Exception {
        request = MockMvcRequestBuilders.post("/publish/flowRule")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assert(responseContent.contains("发布限流规则成功"));
    }

    @Test
    void publishDegradeRule() throws Exception {
        request = MockMvcRequestBuilders.post("/publish/degradeRule")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assert(responseContent.contains("发布降级规则成功"));
    }

    @Test
    void publishAuthority() throws Exception {
        request = MockMvcRequestBuilders.post("/publish/authority")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assert(responseContent.contains("发布权限规则成功"));
    }
}