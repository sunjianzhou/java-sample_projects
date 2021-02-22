package com.example.demoproject.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2021/1/26 14:40
 */
@Data
@Configuration
@PropertySource(value = "classpath:pay.properties")
public class WXConfig {

    @Value("${wxpay.appId}")
    private String payAppId;

    @Value("${wxpay.secret}")
    private String paySecret;

    @Value("${wxpay.mechId}")
    private String mechId;
}
