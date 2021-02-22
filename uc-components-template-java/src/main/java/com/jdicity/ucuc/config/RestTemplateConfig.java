package com.jdicity.ucuc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


/**
 * 网络请求插件.
 *
 * @author liyingda
 * @date 2020-11-19 18:15
 * @version v1.0.0
 */
@Configuration
public class RestTemplateConfig {
    /**
     * 网络请求插件实现.
     *
     * @return 网络请求实例.
     */
    @Bean
    public RestTemplate getRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(500);
        requestFactory.setReadTimeout(1000);
        return new RestTemplate(requestFactory);
    }
}
