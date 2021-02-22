package com.jdicity.ucuc.interceptor;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


/**
 * 拦截器实现.
 *
 * @author liyingda
 * @date 2020-11-19 17:34
 * @version v1.0.0
 */
@SpringBootConfiguration
@Profile("!integrationTest")
public class DrightInterceptorConfig implements WebMvcConfigurer {
    /**
     * 天权拦截器
     */
    @Resource
    private DrightInterceptor drightInterceptor;

    /**
     * 对指定路径添加拦截器.
     *
     * @param registry 注册实体
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(
                drightInterceptor);

        interceptorRegistration.addPathPatterns("/**");

    }
}
