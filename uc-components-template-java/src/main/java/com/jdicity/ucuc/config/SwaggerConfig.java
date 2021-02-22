package com.jdicity.ucuc.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalTime;


/**
 * Swagger 插件.
 *
 * @author liyingda
 * @date 2020-11-18 19:24
 * @version V1.0.0
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {

    /**
     * swagger文档初始化.
     *
     * @return Docket文档对象
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .directModelSubstitute(LocalTime.class, String.class)
                .directModelSubstitute(LocalDate.class, String.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jdicity.ucuc"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * API 相关配置信息.
     *
     * @return API配置信息对象.
     */
    private ApiInfo apiInfo() {

        Contact contact = new Contact(
                "UCUC",
                "https://git.jd.com/ucuc/uc-components-template-java",
                "ucuc@jd.com");

        return new ApiInfoBuilder()
                .title("Hello-World-Template-Java")
                .version("v1.0")
                .contact(contact)
                .license("License UCUC")
                .licenseUrl("https://git.jd.com/ucuc/uc-components-template-java")
                .build();
    }
}
