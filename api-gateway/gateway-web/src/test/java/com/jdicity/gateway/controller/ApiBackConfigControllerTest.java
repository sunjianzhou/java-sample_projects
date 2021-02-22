package com.jdicity.gateway.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdicity.gateway.entity.ApiBackConfig;
import com.jdicity.gateway.mapper.ApiBackConfigMapper;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2021/1/11 16:25
 */

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("integrationTest")
@Sql({"classpath:sql/schema.sql", "classpath:sql/data.sql"})
@Transactional
public class ApiBackConfigControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApiBackConfigMapper apiBackConfigMapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenApiBackConfig_whenAdd_thenRepoAdd() throws Exception {
        String backAddr = "back addr";
        String backPath = "back path";
        String method = "method";
        String interfacePro = "interface protocol";
        Long apiId = 10L;
        ApiBackConfig apiBackConfig = new ApiBackConfig();
        apiBackConfig.setApiId(apiId);
        apiBackConfig.setBackAddr(backAddr);
        apiBackConfig.setBackPath(backPath);
        apiBackConfig.setMethod(method);
        apiBackConfig.setInterfacePro(interfacePro);

        MvcResult mvcResult = mockMvc.perform(post("/backConfig/addBackConfig")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiBackConfig)))
                .andExpect(status().isOk())
                .andReturn();
        ApiBackConfig apiBackConfigReturn = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<ApiBackConfig>() {
                });
        ApiBackConfig apiBackConfigRepo = apiBackConfigMapper.selectById(apiBackConfigReturn.getId());
        assertThat(apiBackConfigRepo.getBackAddr()).isEqualTo(backAddr);
        assertThat(apiBackConfigRepo.getBackPath()).isEqualTo(backPath);
        assertThat(apiBackConfigRepo.getMethod()).isEqualTo(method);
        assertThat(apiBackConfigRepo.getInterfacePro()).isEqualTo(interfacePro);
    }

    @Test
    void givenApiBackConfigId_whenGet_thenReturnApiBackConfig() throws Exception {
        Long id = 7L;
        MvcResult mvcResult = mockMvc.perform(get("/backConfig/findBackConfigById/" + id))
                .andExpect(status().isOk())
                .andReturn();
        ApiBackConfig apiBackConfigReturn = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<ApiBackConfig>() {
                });
        assertThat(apiBackConfigReturn).isNotNull();
        assertThat(apiBackConfigReturn.getMethod()).isEqualTo("method");
    }

    @Test
    void givenApiBackConfig_whenUpdate_thenCompareRepo() throws Exception {
        Long id = 7L;
        String updateMethod = "update method";
        ApiBackConfig apiBackConfig = new ApiBackConfig();
        apiBackConfig.setMethod(updateMethod);
        apiBackConfig.setId(id);
        mockMvc.perform(post("/backConfig/updateBackConfig")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiBackConfig)))
                .andExpect(status().isOk());
        ApiBackConfig apiBackConfigRepo = apiBackConfigMapper.selectById(id);
        assertThat(apiBackConfigRepo.getMethod()).isEqualTo(updateMethod);
    }

    @Test
    void givenApiBackConfigId_whenDelete_thenCheckRepo() throws Exception {
        Long id = 7L;
        ApiBackConfig apiBackConfig = new ApiBackConfig();
        apiBackConfig.setId(id);
        mockMvc.perform(post("/backConfig/deleteBackConfig")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiBackConfig)))
                .andExpect(status().isOk());
        ApiBackConfig apiBackConfigRepo = apiBackConfigMapper.selectById(id);
        assertThat(apiBackConfigRepo).isNull();
    }
}