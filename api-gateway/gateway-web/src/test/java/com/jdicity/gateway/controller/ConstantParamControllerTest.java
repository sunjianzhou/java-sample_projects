package com.jdicity.gateway.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdicity.gateway.entity.ConstantParameter;
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

import static org.junit.Assert.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;


@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("integrationTest")
@Sql(scripts = {"classpath:sql/schema.sql"}, executionPhase = BEFORE_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConstantParamControllerTest {

    private static Long addedId;

    @Autowired
    public MockMvc mvc;

    public MockHttpServletRequestBuilder request = null;

    public static void setAddedId(Long value) {
        ConstantParamControllerTest.addedId = value;
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeAll
    void addConstantParam() throws Exception {
        Long apiId = 123L;
        ObjectMapper objectMapper = new ObjectMapper();
        ConstantParameter constantParameter = new ConstantParameter();
        constantParameter.setApiId(apiId);
        constantParameter.setParameterName("name_a");
        constantParameter.setParameterPos("Header");
        constantParameter.setRequired("Y");
        constantParameter.setDataType("String");
        constantParameter.setValue("value_a");
        constantParameter.setDescription("description");
        request = MockMvcRequestBuilders.post("/constantParam/addConstantParam")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(constantParameter));
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiId").value(apiId))
                .andReturn();
        ConstantParameter constantParameterResponse = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<ConstantParameter>() { });
        ConstantParamControllerTest.setAddedId(constantParameterResponse.getId());
    }

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void findConstantParamById() throws Exception {
        request = MockMvcRequestBuilders.get(
                "/constantParam/findConstantParamById/" + addedId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(addedId));
    }

    @Test
    @Transactional
    @Rollback
    void updateConstantParam() throws Exception {
        String updatedType = "Integer";
        ObjectMapper objectMapper = new ObjectMapper();
        ConstantParameter constantParameter = new ConstantParameter();
        constantParameter.setId(addedId);
        constantParameter.setDataType(updatedType);
        request = MockMvcRequestBuilders.post("/constantParam/updateConstantParam")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(constantParameter));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(addedId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataType").value(updatedType));
    }

    @AfterAll
    void deleteConstantParam() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ConstantParameter constantParameter = new ConstantParameter();
        constantParameter.setId(addedId);
        request = MockMvcRequestBuilders.post("/constantParam/deleteConstantParam")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(constantParameter));
        MvcResult result = mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertTrue(content.contains("删除成功"));
    }
}