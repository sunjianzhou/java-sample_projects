package com.jdicity.gateway.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdicity.gateway.entity.Sentinel;
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
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("integrationTest")
@Sql(scripts = {"classpath:sql/schema.sql"}, executionPhase = BEFORE_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SentinelControllerTest {

    private static Long addedId;

    @Autowired
    public MockMvc mvc;

    public MockHttpServletRequestBuilder request = null;

    public static void setAddedId(Long value) {
        SentinelControllerTest.addedId = value;
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeAll
    void addSentinel() throws Exception {
        Long apiId = 777L;
        ObjectMapper objectMapper = new ObjectMapper();
        Sentinel sentinel = new Sentinel();
        sentinel.setApiId(apiId);
        request = MockMvcRequestBuilders.post("/sentinel/addSentinel")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sentinel));
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiId").value(apiId))
                .andReturn();
        Sentinel apiAppResponse = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<Sentinel>() { });
        SentinelControllerTest.setAddedId(apiAppResponse.getId());
    }

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void findSentinelById() throws Exception {
        request = MockMvcRequestBuilders.get(
                "/sentinel/findSentinelById/" + addedId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(addedId));
    }

    @Test
    @Transactional
    @Rollback
    void updateSentinel() throws Exception {
        Long updatedApiId = 888L;
        Sentinel sentinel = new Sentinel();
        sentinel.setId(addedId);
        sentinel.setApiId(updatedApiId);
        ObjectMapper objectMapper = new ObjectMapper();
        request = MockMvcRequestBuilders.post("/sentinel/updateSentinel")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sentinel));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(addedId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apiId").value(updatedApiId));
    }

    @AfterAll
    void deleteSentinel() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Sentinel targetSentinel = new Sentinel();
        targetSentinel.setId(addedId);
        request = MockMvcRequestBuilders.post("/sentinel/deleteSentinel")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(targetSentinel));
        MvcResult result = mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertEquals("删除成功", content);
    }
}