package com.jdicity.gateway.filters.factory;

import com.jdicity.gateway.filters.ContentTypeChangeFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Change Content Type from others to application/json.
 *
 * @author sunjianzhou
 * @date 2020/12/20 10:27
 */
@Component
public class ContentTypeChangeGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Resource
    ContentTypeChangeFilter contentTypeChangeFilter;

    @Override
    public GatewayFilter apply(Object config) {
        return contentTypeChangeFilter;
    }
}
