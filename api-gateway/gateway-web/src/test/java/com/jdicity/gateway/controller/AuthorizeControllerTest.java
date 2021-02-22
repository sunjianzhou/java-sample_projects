package com.jdicity.gateway.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdicity.gateway.entity.ApiApp;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;


@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("integrationTest")
@Sql("classpath:sql/schema.sql")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthorizeControllerTest {

    private static Long addedId;

    @Autowired
    public MockMvc mvc;

    public MockHttpServletRequestBuilder request = null;

    public static void setAddedId(Long value) {
        AuthorizeControllerTest.addedId = value;
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeAll
    void initData() throws Exception {
        Long apiId = 777L;
        Long appId = 888L;
        ObjectMapper objectMapper = new ObjectMapper();
        ApiApp apiApp = new ApiApp();
        apiApp.setApiId(apiId);
        apiApp.setAppId(appId);
        request = MockMvcRequestBuilders.post("/authorize/addAuthorize")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiApp));
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiId").value(apiId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.appId").value(appId))
                .andReturn();
        ApiApp apiAppResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<ApiApp>() { });
        AuthorizeControllerTest.setAddedId(apiAppResponse.getId());
    }

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void findApplicationById() throws Exception {
        request = MockMvcRequestBuilders.get(
                "/authorize/findAuthorizeById/" + addedId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(addedId));
    }

    @Test
    @Transactional
    @Rollback
    void updateAuthorize() throws Exception {
        Long updatedAppId = 678L;
        ObjectMapper objectMapper = new ObjectMapper();
        ApiApp apiApp = new ApiApp();
        apiApp.setId(addedId);
        apiApp.setAppId(updatedAppId);
        request = MockMvcRequestBuilders.post("/authorize/updateAuthorize")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiApp));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(addedId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.appId").value(updatedAppId));
    }

    @AfterAll
    void deleteAuthorize() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ApiApp targetApiApp = new ApiApp();
        targetApiApp.setId(addedId);
        request = MockMvcRequestBuilders.post("/authorize/deleteAuthorize")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(targetApiApp));
        MvcResult result = mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertEquals("删除成功", content);
    }
}