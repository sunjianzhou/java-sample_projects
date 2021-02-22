package com.jdicity.gateway.nacos;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.jdicity.gateway.constant.NacosPropertiesConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.Executor;


/**
 * Nacos监听类.
 *
 * @author 王立腾
 * @date 2020/12/10 11:16
 */
@Profile("!integrationTest")
@Component
public class NacosDynamicRoute {
    /**
     * 日志对象类
     */
    private static final Logger LOG = LoggerFactory.getLogger(NacosDynamicRoute.class);

    /**
     * Nacos服务中心地址
     */
    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;

    /**
     * 动态路由服务
     */
    @Autowired
    private NacosDynamicRouteService nacosDynamicRouteService;

    /**
     * Nacos启动加载配置信息到gateway路由
     */
    @PostConstruct
    public void init() {
        LOG.info("gateway route init...");
        try {
            ConfigService configService = NacosFactory.createConfigService(serverAddr);
            if (configService == null) {
                LOG.warn("initConfigService fail");
                return;
            }
            String configInfo = configService.getConfig(NacosPropertiesConstant.GATEWAY_RULE_DATA_ID,
                    NacosPropertiesConstant.GATEWAY_RULE_GROUP_ID, 5000);
            LOG.info("获取网关当前设置:\r\n{}", configInfo);
            if (configInfo != null) {
                List<RouteDefinition> definitionList = JSON.parseArray(configInfo, RouteDefinition.class);
                for (RouteDefinition definition : definitionList) {
                    LOG.info("update route : {}", definition);
                    nacosDynamicRouteService.addRoute(definition);
                }
            }
        } catch (Exception e) {
            LOG.error("初始化网关路由时发生错误", e);
        }
        dynamicRouteByNacosListener();
    }

    /**
     * Nacos添加监听
     */
    public void dynamicRouteByNacosListener() {
        try {
            ConfigService configService = NacosFactory.createConfigService(serverAddr);
            String config = configService.getConfig(NacosPropertiesConstant.GATEWAY_RULE_DATA_ID,
                    NacosPropertiesConstant.GATEWAY_RULE_GROUP_ID, 5000);
            LOG.info(config);
            configService.addListener(NacosPropertiesConstant.GATEWAY_RULE_DATA_ID,
                    NacosPropertiesConstant.GATEWAY_RULE_GROUP_ID, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    nacosDynamicRouteService.clearRoute();
                    try {
                        List<RouteDefinition> gatewayRouteDefinitions = JSONObject.parseArray(configInfo, RouteDefinition.class);
                        for (RouteDefinition routeDefinition : gatewayRouteDefinitions) {
                            nacosDynamicRouteService.addRoute(routeDefinition);
                        }
                        nacosDynamicRouteService.publish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }
}
