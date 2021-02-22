package com.jdicity.gateway.filters.factory;

import com.jdicity.gateway.filters.ResponseBodyAesEncryptGatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * aes encrypt
 *
 * @author zhengweibing3
 * @date 2020/12/27/0027 23:15
 */
@Component
public class ResponseBodyAesEncryptGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Resource
    private ResponseBodyAesEncryptGatewayFilter responseBodyAesEncryptGatewayFilter;

    @Override
    public GatewayFilter apply(Object config) {
        return responseBodyAesEncryptGatewayFilter;
    }
}
