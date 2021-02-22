package com.jdicity.ucuc.function;

import com.jdicity.ucuc.constant.enums.ResponseCodeEnum;
import com.jdicity.ucuc.interceptor.threadlocals.InspectionContext;
import com.jdicity.ucuc.interceptor.threadlocals.UserInfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;


@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("integrationTest")
@Sql({"classpath:schema.sql", "classpath:data.sql"})
class HelloWorldControllerTest {

    @Autowired
    private MockMvc mvc;

    public MockHttpServletRequestBuilder request = null;

    @BeforeEach
    public void initData() throws JsonProcessingException {
        String s = "{\"tenantId\":1}";
        ObjectMapper objectMapper = new ObjectMapper();
        UserInfo userInfo = objectMapper.readValue(s, new TypeReference<UserInfo>() {});
        InspectionContext.setContext(userInfo);
    }

    @Test
    @Transactional
    @Rollback
    public void helloWorldPost() throws Exception {
        request = MockMvcRequestBuilders.post("/hello-world-batch")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("[{\"helloWorldName\": \"hello-test\", \"status\": 1}]");
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseCodeEnum.SUCCESS.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void updateHelloWorld() throws Exception {
        request = MockMvcRequestBuilders.put("/hello-world-batch")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("[\n" +
                        "\t{\n" +
                        "\t\t\"deleted\": 0,\n" +
                        "\t\t\"helloWorldName\": \"test-update\",\n" +
                        "\t\t\"dataId\": \"d2e0470fada54b638ccfdd05a6382bfa\"\n" +
                        "\t}\n" +
                        "]");
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseCodeEnum.SUCCESS.getCode()));
    }

    @Test
    @Transactional
    @Rollback
    public void deletedHelloWorld() throws Exception {
        request = MockMvcRequestBuilders.delete("/hello-world-batch")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("[\"0b9d8f71a856404f968b118537892e63\"," +
                        "\"d2e0470fada54b638ccfdd05a6382bfa\"]");
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseCodeEnum.SUCCESS.getCode()));

    }

    @Test
    @Transactional
    @Rollback
    public void helloWorldGetPage() throws Exception {
        request = MockMvcRequestBuilders.get(
                "/hello-world-batch/page")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseCodeEnum.SUCCESS.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty());
    }


    @Test
    @Transactional
    @Rollback
    public void findHelloWorld() throws Exception {
        request = MockMvcRequestBuilders.get("/hello-world-batch/findAll")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseCodeEnum.SUCCESS.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty());
    }

}
