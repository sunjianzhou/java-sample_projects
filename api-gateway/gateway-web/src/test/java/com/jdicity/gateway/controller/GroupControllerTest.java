package com.jdicity.gateway.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdicity.gateway.entity.ServiceGroup;
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
class GroupControllerTest {

    private static Long addedId;

    @Autowired
    public MockMvc mvc;

    public MockHttpServletRequestBuilder request = null;

    public static void setAddedId(Long value) {
        GroupControllerTest.addedId = value;
    }

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeAll
    void addGroup() throws Exception {
        String serviceName = "ServiceName";
        Long userId = 666L;
        ObjectMapper objectMapper = new ObjectMapper();
        ServiceGroup serviceGroup = new ServiceGroup();
        serviceGroup.setServiceName(serviceName);
        serviceGroup.setUserId(userId);
        request = MockMvcRequestBuilders.post("/group/addGroup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(serviceGroup));
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.serviceName").value(serviceName))
                .andReturn();
        ServiceGroup serviceGroupResponse = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<ServiceGroup>() { });
        GroupControllerTest.setAddedId(serviceGroupResponse.getId());
    }

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void findGroupById() throws Exception {
        request = MockMvcRequestBuilders.get(
                "/group/findGroupById/" + addedId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(addedId));
    }

    @Test
    @Transactional
    @Rollback
    void updateGroup() throws Exception {
        String updatedServiceName = "newServiceName";
        ObjectMapper objectMapper = new ObjectMapper();
        ServiceGroup serviceGroup = new ServiceGroup();
        serviceGroup.setId(addedId);
        serviceGroup.setServiceName(updatedServiceName);
        request = MockMvcRequestBuilders.post("/group/updateGroup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(serviceGroup));
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(addedId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serviceName").value(updatedServiceName));
    }

    @AfterAll
    void deleteGroup() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ServiceGroup serviceGroup = new ServiceGroup();
        serviceGroup.setId(addedId);
        request = MockMvcRequestBuilders.post("/group/deleteGroup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(serviceGroup));
        MvcResult result = mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertTrue(content.contains("删除成功"));
    }
}