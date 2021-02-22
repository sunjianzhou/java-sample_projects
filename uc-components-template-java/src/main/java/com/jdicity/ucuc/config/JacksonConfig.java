package com.jdicity.ucuc.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;


/**
 * JacksonConfig: 统一数据序列化.
 *
 * @author wangliteng
 * @date 2020-11-23 11:12
 * @version V1.0.0
 */
@Configuration
public class JacksonConfig {

    /**
     * 空构造函数
     */
    public JacksonConfig() {
    }

    /**
     * 初始化ObjectMapper.
     *
     * @param builder ObjectMapper类对象
     * @return ObjectMapper
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean({ObjectMapper.class})
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }
}
