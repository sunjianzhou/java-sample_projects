package com.jdicity.gateway.filters;

import com.jdicity.gateway.cache.CacheService;
import com.jdicity.gateway.constant.CacheKeys;
import com.jdicity.gateway.constant.TokenProperties;
import com.jdicity.gateway.dto.Authority;
import com.jdicity.gateway.exceptions.CustomErrorException;
import com.jdicity.gateway.filters.constant.FilterOrderEnum;
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
 * api permission check filter.
 *
 * @author zhengweibing3
 * @date 2020/12/17/0017 20:22
 */
@Slf4j
@Component
public class ApiPermissionGatewayFilter implements GatewayFilter, Ordered {

    @Resource
    private CacheService cacheService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String apiName = request.getPath().subPath(request.getPath().elements().size() - 1).value();
        HttpHeaders headers = request.getHeaders();
        String appKey = headers.getFirst(TokenProperties.HEADER_KEY_APP_KEY);
        log.info("----------appkey:{}-------------", appKey);
        if (appKey == null) {
            throw new CustomErrorException("1000", "无appId");
        }
        Object object = cacheService.get(CacheKeys.CACHE_AUTH_KEY);
        if (object == null) {
            throw new CustomErrorException("1000", "当前appId不可用");
        }
        Map<String, Authority> map = (Map<String, Authority>) object;
        if (map.get(appKey) == null || map.get(appKey).getApiName().contains(apiName)) {
            throw new CustomErrorException("1000", "当前appId不可用");
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return FilterOrderEnum.API_PERMISSION.getOrder();
    }
}
