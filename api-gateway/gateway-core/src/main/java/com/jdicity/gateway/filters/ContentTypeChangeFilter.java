package com.jdicity.gateway.filters;

import com.jdicity.gateway.filters.constant.FilterOrderEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * ContentTypeChangeFilter.
 *
 * @author sunjianzhou
 * @date 2020/12/20 10:11
 */
@Slf4j
@Component
public class ContentTypeChangeFilter implements GatewayFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("=================ContentTypeChangeFilter=================");
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();
        mutate.header("Content-Type", MediaType.APPLICATION_JSON_VALUE).build();
        ServerWebExchange mutableExchange = exchange.mutate().request(mutate.build()).build();
        return chain.filter(mutableExchange);
    }

    @Override
    public int getOrder() {
        return FilterOrderEnum.CONTENT_TYPE_CHANGE.getOrder();
    }
}
