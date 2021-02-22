package com.jdicity.gateway;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdicity.gateway.entity.Filter;
import com.jdicity.gateway.mapper.FilterMapper;
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
 * filter curd test
 *
 * @author zhengweibing3
 * @date 2021/1/4/0004 17:28
 */
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("integrationTest")
@Sql({"classpath:sql/schema.sql", "classpath:sql/data.sql"})
@Transactional
class FilterControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FilterMapper filterMapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Integer filterRepoId = 7;

    @Test
    void sanity() {

    }

    @Test
    void givenFilter_whenAdd_thenReturnSuccess() throws Exception {
        String desc = "filter desc";
        String name = "filter name";
        String value = "filter value";
        Long api_id = 3L;
        Filter filter = new Filter();
        filter.setFilterDesc(desc);
        filter.setFilterName(name);
        filter.setFilterValue(value);
        filter.setApiId(api_id);
        MvcResult mvcResult = mockMvc.perform(post("/filter/addFilter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filter)))
                .andExpect(status().isOk())
                .andReturn();
        Filter filterResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<Filter>() { });
        Filter filterRepo = filterMapper.selectById(filterResponse.getId());
        assertThat(filterRepo.getFilterDesc()).isEqualTo(desc);
        assertThat(filterRepo.getFilterName()).isEqualTo(name);
        assertThat(filterRepo.getFilterValue()).isEqualTo(value);
    }

    @Test
    void givenFilterId_whenGet_thenReturnFilter() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/filter/findFilterById/" + filterRepoId))
                .andExpect(status().isOk())
                .andReturn();
        Filter filter = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<Filter>() { });
        assertThat(filter.getId()).isEqualTo(filterRepoId.intValue());
    }

    @Test
    void givenFilterId_whenDelete_thenReturnFilterId() throws Exception {
        Filter filterRepo = filterMapper.selectById(filterRepoId);
        assertThat(filterRepo).isNotNull();
        mockMvc.perform(post("/filter/deleteFilter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filterRepo)))
                .andExpect(status().isOk());
        Filter filterDelete = filterMapper.selectById(filterRepoId);
        assertThat(filterDelete).isNull();
    }

    @Test
    void givenFilter_whenUpdate_thenReturnFilter() throws Exception {
        String updateDesc = "update desc";
        Filter filterRepo = filterMapper.selectById(filterRepoId);
        assertThat(filterRepo).isNotNull();
        filterRepo.setFilterDesc(updateDesc);
        mockMvc.perform(post("/filter/updateFilter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filterRepo)))
                .andExpect(status().isOk());
        Filter updateFilter = filterMapper.selectById(filterRepoId);
        assertThat(updateFilter.getFilterDesc()).isEqualTo(updateDesc);
    }

}
