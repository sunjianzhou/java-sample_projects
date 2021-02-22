package com.jdicity.gateway.filters.function;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2020/12/24 18:35
 */
@Slf4j
@Component
public class RequestBodyGetData implements RewriteFunction<String, String> {

    @SneakyThrows
    @Override
    public Publisher<String> apply(ServerWebExchange exchange, String body) {
        log.info("=================RequestBodyModify=================");
        ServerHttpRequest request = exchange.getRequest();

        if (request.getMethod() != HttpMethod.POST) {
            return Mono.just(body);
        }
        ObjectMapper bodyJsonObject = new ObjectMapper();
        HashMap<String, Object> bodyMap;
        bodyMap = bodyJsonObject.readValue(body, HashMap.class);

        Object dataObject = bodyMap.get("data");
        if (null == dataObject) {
            return Mono.just("{}");
        } else {
            log.info("data的内容为：{}", dataObject.toString());
            return Mono.just(new ObjectMapper().writeValueAsString(dataObject));
        }
    }
}
