package com.jdicity.ucuc.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * MybatisPlusConfig: 分页插件.
 *
 * @author liyingda
 * @date 2020-11-19 18:12
 * @version v1.0.0
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 分页插件实现.
     *
     * @return page
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }
}
