package com.jdicity.gateway.filters;

import com.jdicity.gateway.constant.HeaderEnum;
import com.jdicity.gateway.constant.ResponseCodeEnum;
import com.jdicity.gateway.exceptions.CustomErrorException;
import com.jdicity.gateway.filters.constant.FilterOrderEnum;
import com.jdicity.gateway.service.AccessTokenCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * Access token check for app client.
 *
 * @author sunjianzhou
 * @date 2020/12/21 16:48
 */
@Slf4j
@Component
public class AccessTokenCheckFilter implements GatewayFilter, Ordered {

    @Autowired
    private AccessTokenCheckService accessTokenCheckService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("=========AccessTokenCheckFilter===========");
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String accessToken = headers.getFirst(HeaderEnum.ACCESS_TOKEN.getValue());
        log.info("客户端accessToken：{}", accessToken);
        String clientId = headers.getFirst(HeaderEnum.CLIENT_ID.getValue());
        log.info("客户端clientId：{}", clientId);

        boolean result = accessTokenCheckService.checkAccessToken(accessToken, clientId);
        if (!result) {
            throw new CustomErrorException(ResponseCodeEnum.ACCESS_TOKEN_ERROR);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return FilterOrderEnum.ACCESS_TOKEN_CHECK.getOrder();
    }
}
