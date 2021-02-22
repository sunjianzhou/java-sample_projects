package com.jdicity.ucuc.interceptor;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


/**
 * Token 验证拦截器配置.
 *
 * @author liyingda
 * @date 2020-11-19 18:15
 * @version v1.0.0
 */
@SpringBootConfiguration
@Profile("!integrationTest")
public class TokenVerifyInterceptorConfig implements WebMvcConfigurer {
    /**
     * TokenVerifyInterceptor
     */
    @Resource
    private TokenVerifyInterceptor tokenVerifyInterceptor;

    /**
     * 对指定路径添加拦截器.
     *
     * @param registry 注册实体
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(
                tokenVerifyInterceptor);

        interceptorRegistration.addPathPatterns("/hello-world-batch/**");
    }
}
