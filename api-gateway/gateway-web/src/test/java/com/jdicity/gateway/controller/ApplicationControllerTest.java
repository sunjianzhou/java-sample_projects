package com.jdicity.gateway.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdicity.gateway.entity.Application;
import com.jdicity.gateway.mapper.ApplicationMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * application curd test
 *
 * @author zhengweibing3
 * @date 2021/1/6/0006 11:44
 */
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("integrationTest")
@Sql({"classpath:sql/schema.sql", "classpath:sql/data.sql"})
@Transactional
public class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationMapper applicationMapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenApplication_whenAdd_thenStoreInDatabase() throws Exception {
        Application application = new Application();
        Long userId = 10L;
        String appName = "app name";
        application.setName(appName);
        application.setUserId(userId);
        application.setAppKey("key");
        application.setAppSecret("key");
        application.setPubKey("key");
        application.setAesPrivateKey("key");
        MvcResult mvcResult = mockMvc.perform(post("/application/addApplication")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(application)))
                .andExpect(status().isOk())
                .andReturn();
        Application applicationReturn = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<Application>() { });
        Application applicationRepo = applicationMapper.selectById(applicationReturn);
        assertThat(applicationRepo.getName()).isEqualTo(appName);
        assertThat(applicationRepo.getId()).isNotNull();
        assertThat(applicationRepo.getUserId()).isEqualTo(userId.intValue());
    }

    @Test
    void givenApplicationId_whenGet_thenReturnApplication() throws Exception {
        Long id = 4L;
        MvcResult mvcResult = mockMvc.perform(get("/application/findApplicationById/" + id))
                .andExpect(status().isOk())
                .andReturn();
        Application applicationReturn = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<Application>() { });
        assertThat(applicationReturn.getId()).isEqualTo(id.intValue());
        assertThat(applicationReturn.getName()).isEqualTo("name");
    }

    @Test
    void givenApplication_whenUpdate_thenRepoChange() throws Exception {
        Long id = 4L;
        Application application = new Application();
        String appName = "update name";
        application.setId(id);
        application.setName(appName);
        application.setUserId(11L);
        application.setAppKey("key");
        application.setAppSecret("key");
        application.setPubKey("key");
        application.setAesPrivateKey("key");
        mockMvc.perform(post("/application/updateApplication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(application)))
                .andExpect(status().isOk());
        Application applicationRepo = applicationMapper.selectById(id);
        assertThat(applicationRepo.getName()).isEqualTo(appName);
    }

    @Test
    void givenApplication_whenDelete_thenRepoDelete() throws Exception {
        Long id = 4L;
        Application application = new Application();
        application.setId(id);
        MvcResult mvcResult = mockMvc.perform(post("/application/deleteApplication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(application)))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8)).isEqualTo("删除成功");
        Application applicationRepo = applicationMapper.selectById(id);
        assertThat(applicationRepo).isNull();
    }

}
