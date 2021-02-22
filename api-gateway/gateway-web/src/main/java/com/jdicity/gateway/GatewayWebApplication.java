package com.jdicity.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类.
 *
 * @author zhengweibing3
 * @date 2020/12/17 15:16
 */
@SpringBootApplication
@MapperScan("com.jdicity.gateway.mapper")
public class GatewayWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayWebApplication.class, args);
    }

}
