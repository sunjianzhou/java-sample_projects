package com.jdicity.gateway.nacos;

import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * 动态路由服务.
 *
 * @author 王立腾
 * @date 2020/12/10 11:16
 */
@Profile("!integrationTest")
@Service
public class NacosDynamicRouteService implements ApplicationEventPublisherAware {

    /**
     * 路由读写对象
     */
    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    /**
     * 上下文发布对象
     */
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 方法动态路由集合
     */
    private static final List<String> ROUTE_LIST = new ArrayList<>();

    /**
     * 删除路由对象信息
     */
    public void clearRoute() {
        for (String id : ROUTE_LIST) {
            this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
        }
        ROUTE_LIST.clear();
    }

    /**
     * 添加路由对象信息
     *
     * @param definition 路由对象
     */
    public void addRoute(RouteDefinition definition) {
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            ROUTE_LIST.add(definition.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 刷新路由信息
     */
    public void publish() {
        this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this.routeDefinitionWriter));
    }

    /**
     * 设置上下文发布者对象
     *
     * @param applicationEventPublisher 发布者
     */
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
