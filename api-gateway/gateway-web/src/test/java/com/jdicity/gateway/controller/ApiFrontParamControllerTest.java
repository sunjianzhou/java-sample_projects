package com.jdicity.gateway.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdicity.gateway.entity.ApiFrontParameter;
import com.jdicity.gateway.mapper.ApiFrontParameterMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Write description here.
 *
 * @author zhengweibing3
 * @date 2021/1/6/0006 17:36
 */
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("integrationTest")
@Sql({"classpath:sql/schema.sql", "classpath:sql/data.sql"})
@Transactional
public class ApiFrontParamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApiFrontParameterMapper apiFrontParameterMapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenApiFrontParameter_whenAdd_thenRepoAdd() throws Exception {
        String parameterName = "parameter name";
        ApiFrontParameter apiFrontParameter = new ApiFrontParameter(null, parameterName, "",
                "", "", "", 11L);
        MvcResult mvcResult = mockMvc.perform(post("/frontParam/addApiFrontParam")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiFrontParameter)))
                .andExpect(status().isOk())
                .andReturn();
        ApiFrontParameter apiFrontParameterReturn = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<ApiFrontParameter>() { });
        ApiFrontParameter apiFrontParameterRepo = apiFrontParameterMapper.selectById(apiFrontParameterReturn.getId());
        assertThat(apiFrontParameterRepo).isNotNull();
        assertThat(apiFrontParameterRepo.getParameterName()).isEqualTo(parameterName);
    }

    @Test
    void givenApiFrontParameterId_whenGet_thenReturnApiFrontParameter() throws Exception {
        Long id = 2L;
        MvcResult mvcResult = mockMvc.perform(get("/frontParam/findApiFrontParamById/" + id))
                .andExpect(status().isOk())
                .andReturn();
        ApiFrontParameter apiFrontParameterReturn = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<ApiFrontParameter>() { });
        ApiFrontParameter apiFrontParameterRepo = apiFrontParameterMapper.selectById(id);
        assertThat(apiFrontParameterReturn.getParameterName()).isEqualTo(apiFrontParameterRepo.getParameterName());
    }

    @Test
    void givenApiFrontParameter_whenUpdate_thenRepoUpdate() throws Exception {
        Long id = 2L;
        String updateParameterName = "update parameter name";
        ApiFrontParameter apiFrontParameter = new ApiFrontParameter(id, updateParameterName, "",
                "", "", "", 11L);
        mockMvc.perform(post("/frontParam/updateApiFrontParam")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiFrontParameter)))
                .andExpect(status().isOk());

        ApiFrontParameter apiFrontParameterRepo = apiFrontParameterMapper.selectById(id);
        assertThat(apiFrontParameterRepo).isNotNull();
        assertThat(apiFrontParameterRepo.getParameterName()).isEqualTo(updateParameterName);
    }

    @Test
    void givenApiFrontParameterId_whenDelete_thenRepoDelete() throws Exception {
        Long id = 2L;
        ApiFrontParameter apiFrontParameter = new ApiFrontParameter(id, null, null,
                null, null, null, null);
        mockMvc.perform(post("/frontParam/deleteApiFrontParam")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiFrontParameter)))
                .andExpect(status().isOk());
        ApiFrontParameter apiFrontParameterRepo = apiFrontParameterMapper.selectById(id);
        assertThat(apiFrontParameterRepo).isNull();
    }
}
