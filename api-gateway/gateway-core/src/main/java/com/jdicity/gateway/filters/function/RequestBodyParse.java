package com.jdicity.gateway.filters.function;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdicity.gateway.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/**
 * 请求体解析过滤。
 *
 * @author sunjianzhou
 * @date 2020/12/17 15:12
 */
@Slf4j
@Component
public class RequestBodyParse implements RewriteFunction<String, String> {

    @Override
    public Publisher<String> apply(ServerWebExchange exchange, String body) {
        log.info("=================RequestBodyParse=================");
        ServerHttpRequest request = exchange.getRequest();

        if (request.getMethod() != HttpMethod.POST) {
            return Mono.just(body);
        }

        HttpHeaders headers = request.getHeaders();
        MediaType contentType = headers.getContentType();

        log.info("原始body:{}", body);

        // 解析body业务数据
        HashMap<String, Object> bodyJsonObject;
        ObjectMapper mapper = new ObjectMapper();
        try {
            bodyJsonObject = RequestUtils.bodyParse(contentType, body);
            String modifyBody = mapper.writeValueAsString(bodyJsonObject);
            log.info("数据解析成功：{}", modifyBody);
            return Mono.just(modifyBody);
        } catch (Exception e) {
            log.error("数据解析失败：" + e.toString());
            return Mono.just(body);
        }
    }
}
