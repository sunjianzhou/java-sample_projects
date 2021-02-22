package com.jdicity.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/28 14:53
 */


@Profile("!prod")
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    private static Map<String, String> testMap = new HashMap<>();

    @GetMapping("/get")
    public String testGet() {
        return "get:" + testMap.toString();
    }

    @PostMapping("/post")
    public String postTest(HttpServletRequest request, @RequestBody Map<String, String> queryMap) {
        // 清理数据
        testMap.clear();
        for (Map.Entry<String, String> entry : queryMap.entrySet()) {
            testMap.put(entry.getKey(), entry.getValue());
        }
        //拼装header
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headNames = request.getHeaderNames();
        while (headNames.hasMoreElements()) {
            String headName = headNames.nextElement();
            sb.append(headName).append(": ").append(request.getHeader(headName)).append("; ");
        }
        String result =  String.format("request header: %s, body params: %s", sb.toString(), testMap.toString());
        log.info("request:  {}", request);
        return result;
    }
}
