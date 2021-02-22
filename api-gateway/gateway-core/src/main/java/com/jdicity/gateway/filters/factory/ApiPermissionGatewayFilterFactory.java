package com.jdicity.gateway.filters.factory;

import com.jdicity.gateway.filters.ApiPermissionGatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Write description here.
 *
 * @author zhengweibing3
 * @date 2020/12/17/0017 21:13
 */
@Component
public class ApiPermissionGatewayFilterFactory  extends AbstractGatewayFilterFactory<Object> {

    @Resource
    private ApiPermissionGatewayFilter apiPermissionGatewayFilter;

    @Override
    public GatewayFilter apply(Object config) {
        return apiPermissionGatewayFilter;
    }


}
