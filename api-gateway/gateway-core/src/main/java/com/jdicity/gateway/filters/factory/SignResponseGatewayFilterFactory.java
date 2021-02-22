package com.jdicity.gateway.filters.factory;

import com.jdicity.gateway.filters.SignResponseGatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Write description here.
 *
 * @author zhengweibing3
 * @date 2020/12/23/0023 17:52
 */
@Component
public class SignResponseGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Resource
    private SignResponseGatewayFilter signResponseGatewayFilter;

    @Override
    public GatewayFilter apply(Object config) {
        return signResponseGatewayFilter;
    }
}
