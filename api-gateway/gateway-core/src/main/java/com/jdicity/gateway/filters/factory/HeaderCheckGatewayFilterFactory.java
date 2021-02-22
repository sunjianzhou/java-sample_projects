package com.jdicity.gateway.filters.factory;

import com.jdicity.gateway.filters.HeaderCheckFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Header Check factory.
 *
 * @author sunjianzhou
 * @date 2020/12/18 16:04
 */
@Component
public class HeaderCheckGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Resource
    private HeaderCheckFilter headerCheckFilter;

    @Override
    public GatewayFilter apply(Object config) {
        return headerCheckFilter;
    }
}
