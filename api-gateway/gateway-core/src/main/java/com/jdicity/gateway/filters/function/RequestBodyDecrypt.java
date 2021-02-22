package com.jdicity.gateway.filters.function;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdicity.gateway.constant.HeaderEnum;
import com.jdicity.gateway.service.DecryptService;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2020/12/25 16:35
 */
@Slf4j
@Component
public class RequestBodyDecrypt implements RewriteFunction<String, String> {

    @Resource
    private DecryptService decryptService;

    @Override
    public Publisher<String> apply(ServerWebExchange exchange, String body) {
        log.info("=================RequestBodyDecrypt=================");
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();

        String aesKey = headers.getFirst(HeaderEnum.AES_KEY.getValue());
        log.info("原始body:{}", body);

        // 解析body业务数据
        HashMap<String, Object> bodyMap;
        ObjectMapper bodyJsonObject = new ObjectMapper();
        ObjectMapper mapper = new ObjectMapper();
        try {
            bodyMap = bodyJsonObject.readValue(body, HashMap.class);
            bodyMap.put("data", decryptService.decryptData(aesKey, bodyMap.get("data").toString()));

            String modifyBody = mapper.writeValueAsString(bodyMap);
            log.info("数据解析成功：{}", modifyBody);
            return Mono.just(modifyBody);
        } catch (Exception e) {
            log.error("数据解析失败：" + e.toString());
            return Mono.just(body);
        }
    }

}
