package com.example.demoproject.controller;

import com.example.demoproject.config.WXConfig;
import com.example.demoproject.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2021/1/25 20:14
 */
@RestController
@RequestMapping("api/v1/test")
@PropertySource({"classpath:pay.properties"})
public class TestConfigController {

    @Value("${wxpay.appId}")
    private String payAppId;

    @Value("${wxpay.secret}")
    private String paySecret;

    // 这个和上面是两种方式
    @Autowired
    private WXConfig wxConfig;

    @GetMapping("get_config")
    public JsonData testConfig(){
        Map<String, String> map = new HashMap<>();
        map.put("appId", payAppId);
        map.put("appSecret", paySecret);

        return JsonData.buildSuccess(map);
    }

    @GetMapping("get_config_by_class")
    public JsonData testConfigByClass(){
        Map<String, String> map = new HashMap<>();
        map.put("appId", wxConfig.getPayAppId());
        map.put("appSecret", wxConfig.getPaySecret());
        map.put("mechId", wxConfig.getMechId());

        return JsonData.buildSuccess(map);
    }

    @GetMapping("exception")
    public JsonData testException(){
        int num = 1/0;
        return JsonData.buildSuccess("");
    }
}
