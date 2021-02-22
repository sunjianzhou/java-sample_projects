package com.jdicity.gateway.filters;

import com.jdicity.gateway.cache.CacheService;
import com.jdicity.gateway.constant.CacheKeys;
import com.jdicity.gateway.constant.TokenProperties;
import com.jdicity.gateway.dto.Authority;
import com.jdicity.gateway.exceptions.CustomErrorException;
import com.jdicity.gateway.filters.constant.FilterOrderEnum;
import com.jdicity.gateway.util.JWTUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Write description here.
 *
 * @author zhengweibing3
 * @date 2020/12/16/0016 17:19
 */
@Slf4j
@Component
public class TokenCheckGatewayFilter implements GatewayFilter, Ordered {

    @Resource
    private CacheService cacheService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("==========token filter============");
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst("token");

        Claims claims = JWTUtils.parseToken(token);
        if (claims == null) {
            throw new CustomErrorException("10000", "token非法");
        }
        String appKey = (String) claims.get(TokenProperties.APP_KEY);
        Object object = cacheService.get(CacheKeys.CACHE_AUTH_KEY);
        if (object == null) {
            throw new CustomErrorException("1000", "当前appId不可用");
        }
        Map<String, Authority> map = (Map<String, Authority>) object;
        if (map.get(appKey) == null) {
            throw new CustomErrorException("1000", "当前appId不可用");
        }

        ServerHttpRequest mutableReq = request.mutate().header(TokenProperties.HEADER_KEY_APP_KEY, appKey).build();
        ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
        return chain.filter(mutableExchange);
    }

    @Override
    public int getOrder() {
        return FilterOrderEnum.TOKEN_CHECK.getOrder();
    }
}
