package com.jdicity.gateway.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdicity.gateway.entity.ApiFrontConfig;
import com.jdicity.gateway.mapper.ApiFrontConfigMapper;
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
 * api front config curd test.
 *
 * @author zhengweibing3
 * @date 2021/1/6/0006 16:13
 */
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("integrationTest")
@Sql({"classpath:sql/schema.sql", "classpath:sql/data.sql"})
@Transactional
public class ApiFrontConfigControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApiFrontConfigMapper apiFrontConfigMapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenApiFrontConfig_whenAdd_thenRepoAdd() throws Exception {
        String frontPath = "front path";
        String method = "method";
        String interfacePro = "interface protocol";
        Long apiId = 10L;
        ApiFrontConfig apiFrontConfig = new ApiFrontConfig(null, frontPath, method, interfacePro, apiId);
        MvcResult mvcResult = mockMvc.perform(post("/frontConfig/addFrontConfig")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiFrontConfig)))
                .andExpect(status().isOk())
                .andReturn();
        ApiFrontConfig apiFrontConfigReturn = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<ApiFrontConfig>() { });
        ApiFrontConfig apiFrontConfigRepo = apiFrontConfigMapper.selectById(apiFrontConfigReturn.getId());
        assertThat(apiFrontConfigRepo.getFrontPath()).isEqualTo(frontPath);
        assertThat(apiFrontConfigRepo.getApiId()).isEqualTo(apiId);
        assertThat(apiFrontConfigRepo.getInterfacePro()).isEqualTo(interfacePro);
        assertThat(apiFrontConfigRepo.getMethod()).isEqualTo(method);
    }

    @Test
    void givenApiFrontConfigId_whenGet_thenReturnApiFrontConfig () throws Exception {
        Long id = 7L;
        MvcResult mvcResult = mockMvc.perform(get("/frontConfig/findFrontConfigById/" + id))
                .andExpect(status().isOk())
                .andReturn();
        ApiFrontConfig apiFrontConfigReturn = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<ApiFrontConfig>() { });
        assertThat(apiFrontConfigReturn).isNotNull();
        assertThat(apiFrontConfigReturn.getMethod()).isEqualTo("method");
    }

    @Test
    void givenApiFrontConfig_whenUpdate_thenCompareRepo() throws Exception {
        Long id = 7L;
        String updateMethod = "update method";
        ApiFrontConfig apiFrontConfig = new ApiFrontConfig(id, "", updateMethod, "", 1L);
        mockMvc.perform(post("/frontConfig/updateFrontConfig")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiFrontConfig)))
                .andExpect(status().isOk());
        ApiFrontConfig apiFrontConfigRepo = apiFrontConfigMapper.selectById(id);
        assertThat(apiFrontConfigRepo.getMethod()).isEqualTo(updateMethod);
    }

    @Test
    void givenApiFrontConfigId_whenDelete_thenCheckRepo() throws Exception{
        Long id = 7L;
        ApiFrontConfig apiFrontConfig = new ApiFrontConfig(id, "", "", "", 1L);
        mockMvc.perform(post("/frontConfig/deleteFrontConfig")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiFrontConfig)))
                .andExpect(status().isOk());
        ApiFrontConfig apiFrontConfigRepo = apiFrontConfigMapper.selectById(id);
        assertThat(apiFrontConfigRepo).isNull();
    }

}
