package com.jdicity.gateway.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdicity.gateway.entity.ApiBackParameter;
import com.jdicity.gateway.mapper.ApiBackParameterMapper;
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
 * @author zhengweibing3
 * @date 2021/1/6/0006 20:06
 */
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("integrationTest")
@Sql({"classpath:sql/schema.sql", "classpath:sql/data.sql"})
@Transactional
public class ApiBackParamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApiBackParameterMapper apiBackParameterMapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenApiBackParameter_whenAdd_thenRepoAdd() throws Exception {
        String pathName = "path name";
        ApiBackParameter apiBackParameter = new ApiBackParameter(null, pathName, "",
                "", "", "", 1L);
        MvcResult mvcResult = mockMvc.perform(post("/backParam/addBackParam")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiBackParameter)))
                .andExpect(status().isOk())
                .andReturn();
        ApiBackParameter apiBackParameterReturn = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<ApiBackParameter>() {});
        ApiBackParameter apiBackParameterRepo = apiBackParameterMapper.selectById(apiBackParameterReturn.getId());
        assertThat(apiBackParameterRepo).isNotNull();
        assertThat(apiBackParameterRepo.getParameterName()).isEqualTo(pathName);
    }

    @Test
    void givenApiBackParameterId_whenGet_thenReturnApiBackParameter() throws Exception {
        Long id = 2L;
        MvcResult mvcResult = mockMvc.perform(get("/backParam/findBackParamById/" + id))
                .andExpect(status().isOk())
                .andReturn();
        ApiBackParameter apiBackParameter = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<ApiBackParameter>() { });
        ApiBackParameter apiBackParameterRepo = apiBackParameterMapper.selectById(id);
        assertThat(apiBackParameterRepo.getId()).isEqualTo(apiBackParameter.getId());
        assertThat(apiBackParameterRepo.getParameterName()).isEqualTo(apiBackParameter.getParameterName());
    }

    @Test
    void givenApiBackParameter_whenUpdate_thenRepoChange() throws Exception {
        Long id = 2L;
        String updatePathName = "update path name";
        ApiBackParameter apiBackParameter = new ApiBackParameter(id, updatePathName, "",
                "", "", "", 1L);
        mockMvc.perform(post("/backParam/updateBackParam")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiBackParameter)))
                .andExpect(status().isOk());
        ApiBackParameter apiBackParameterRepo = apiBackParameterMapper.selectById(id);
        assertThat(apiBackParameterRepo).isNotNull();
        assertThat(apiBackParameterRepo.getParameterName()).isEqualTo(updatePathName);
    }

    @Test
    void givenApiBackParameter_whenDelete_thenRepoDelete() throws Exception {
        Long id = 2L;
        ApiBackParameter apiBackParameter = new ApiBackParameter(id, "", "",
                "", "", "", 1L);
        mockMvc.perform(post("/backParam/deleteBackParam")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(apiBackParameter)))
                .andExpect(status().isOk());
        ApiBackParameter apiBackParameterRepo = apiBackParameterMapper.selectById(id);
        assertThat(apiBackParameterRepo).isNull();
    }

}
