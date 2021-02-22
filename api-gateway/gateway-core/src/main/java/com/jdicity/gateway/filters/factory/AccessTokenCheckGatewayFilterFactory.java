package com.jdicity.gateway.filters.factory;

import com.jdicity.gateway.filters.AccessTokenCheckFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2020/12/21 17:23
 */
@Component
public class AccessTokenCheckGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Resource
    private AccessTokenCheckFilter accessTokenCheckFilter;

    @Override
    public GatewayFilter apply(Object config) {
        return accessTokenCheckFilter;
    }
}
