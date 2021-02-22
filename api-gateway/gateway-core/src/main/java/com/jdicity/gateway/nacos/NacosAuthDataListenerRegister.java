package com.jdicity.gateway.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.exception.NacosException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdicity.gateway.cache.CacheService;
import com.jdicity.gateway.constant.CacheKeys;
import com.jdicity.gateway.constant.NacosPropertiesConstant;
import com.jdicity.gateway.dto.Authority;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Write description here.
 *
 * @author zhengweibing3
 * @date 2020/12/22/0022 16:52
 */
@Profile("!integrationTest")
@Service
@Slf4j
public class NacosAuthDataListenerRegister {

    /**
     * nacos服务地址
     */
    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddress;

    @Resource
    private CacheService cacheService;

    /**
     * 添加监听
     */
    @PostConstruct
    public void setUp() throws NacosException {
        ObjectMapper objectMapper = new ObjectMapper();
        ConfigService configService =  NacosFactory.createConfigService(serverAddress);
        Map<String, Authority> map = new HashMap<>();
        log.info("启动加载权限");
        try {
            String configInfo = configService.getConfig(NacosPropertiesConstant.AUTH_DATA_ID,
                NacosPropertiesConstant.AUTH_GROUP_ID, 5000);
            if (StringUtils.isNotBlank(configInfo)) {
                List<Authority> authorityList = objectMapper.readValue(configInfo, new TypeReference<List<Authority>>() { });
                authorityList.forEach(authority -> map.put(authority.getAppKey(), authority));
            }
        } catch (JsonProcessingException e) {
            log.info("启动加载权限错误:", e);
        }
        configService.addListener(NacosPropertiesConstant.AUTH_DATA_ID, NacosPropertiesConstant.AUTH_GROUP_ID, new AbstractListener() {
            @Override
            public void receiveConfigInfo(String config) {
                log.info("更新权限");
                try {
                    List<Authority> authorityList = objectMapper.readValue(config, new TypeReference<List<Authority>>() { });
                    authorityList.forEach(authority -> map.put(authority.getAppKey(), authority));
                } catch (JsonProcessingException e) {
                    log.info("更新权限失败:", e);
                }
            }
        });
        cacheService.set(CacheKeys.CACHE_AUTH_KEY, map);
    }
}
