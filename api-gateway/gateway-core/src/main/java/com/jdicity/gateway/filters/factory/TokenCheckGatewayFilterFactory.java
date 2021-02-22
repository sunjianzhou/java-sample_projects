package com.jdicity.gateway.filters.factory;

import com.jdicity.gateway.filters.TokenCheckGatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Write description here.
 *
 * @author zhengweibing3
 * @date 2020/12/16/0016 17:19
 */
@Component
public class TokenCheckGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Resource
    private TokenCheckGatewayFilter tokenCheckGatewayFilter;

    @Override
    public GatewayFilter apply(Object config) {
        return tokenCheckGatewayFilter;
    }
}
