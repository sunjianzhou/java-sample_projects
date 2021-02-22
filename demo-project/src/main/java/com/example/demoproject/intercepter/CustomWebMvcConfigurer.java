package com.example.demoproject.intercepter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类。
 *
 * @author sunjianzhou
 * @date 2021/1/27 19:17
 */
@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //拦截器，当有多个时，先注册，先拦截。
        registry.addInterceptor(getLoginInterceptor()).addPathPatterns("/api/v1/pri/**");
        registry.addInterceptor(new SecondIntercepter()).addPathPatterns("/api/v1/pri/**");

        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Bean
    public LoginIntercepter getLoginInterceptor(){
        return new LoginIntercepter();
    }
}
