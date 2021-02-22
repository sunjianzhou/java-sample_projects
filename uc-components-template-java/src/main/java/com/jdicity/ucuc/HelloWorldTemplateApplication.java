package com.jdicity.ucuc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * HelloWorldTemplateApplication.
 *
 * @author liyingda
 * @date 2020-11-19 18:05
 * @version v1.0.0
 */
@MapperScan(basePackages = {"com.jdicity.ucuc.dao"})
@SpringBootApplication
public class HelloWorldTemplateApplication {
    /**
     * Main entry.
     *
     * @param args args
     */
    public static void main(String[] args) {
        SpringApplication.run(HelloWorldTemplateApplication.class, args);
    }
}
