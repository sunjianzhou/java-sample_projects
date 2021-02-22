package com.jdicity.gateway.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdicity.gateway.entity.ApiInfo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("integrationTest")
@Sql(scripts = {"classpath:sql/schema.sql"}, executionPhase = BEFORE_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApiInfoControllerTest {

    private static Long addedId;

    @Autowired
    public MockMvc mvc;

    public MockHttpServletRequestBuilder request = null;

    public static void setAddedId(Long value) {
        ApiInfoControllerTest.addedId = value;
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @BeforeAll
    void addApiInfo() throws Exception {
        String apiName = "apiName";
        Long serviceId = 666L;
        ObjectMapper objectMapper = new ObjectMapper();
        ApiInfo apiInfo = new ApiInfo();
        apiInfo.setApiName(apiName);
        apiInfo.setSerivceId(serviceId);
        request = MockMvcRequestBuilders.post("/apiInfo/addApiInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiInfo));
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiName").value(apiName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serivceId").value(serviceId))
                .andReturn();
        ApiInfo apiInfoResponse = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<ApiInfo>() { });
        ApiInfoControllerTest.setAddedId(apiInfoResponse.getId());
    }

    @Test
    void findApiInfoById() throws Exception {
        request = MockMvcRequestBuilders.get(
                "/apiInfo/findApiInfoById/" + addedId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(addedId));
    }

    @Test
    void updateApiInfo() throws Exception {
        String updatedApiName = "updatedApiName";
        ObjectMapper objectMapper = new ObjectMapper();
        ApiInfo apiInfo = new ApiInfo();
        apiInfo.setId(addedId);
        apiInfo.setApiName(updatedApiName);
        request = MockMvcRequestBuilders.post("/apiInfo/updateApiInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiInfo));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(addedId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiName").value(updatedApiName));
    }

    @AfterAll
    void deleteApiInfo() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ApiInfo targetApiInfo = new ApiInfo();
        targetApiInfo.setId(addedId);
        request = MockMvcRequestBuilders.post("/apiInfo/deleteApiInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(targetApiInfo));
        MvcResult result = mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        result.getResponse().setCharacterEncoding("UTF-8");
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertEquals("删除成功", content);
    }
}