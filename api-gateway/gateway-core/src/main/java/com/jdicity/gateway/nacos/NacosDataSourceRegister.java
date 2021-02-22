package com.jdicity.gateway.nacos;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jdicity.gateway.constant.NacosPropertiesConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Write description here.
 *
 * @author zhengweibing3
 * @date 2020/12/18/0018 17:26
 */
@Profile("!integrationTest")
@Component
public class NacosDataSourceRegister {

    /**
     * nacos服务地址
     */
    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddress;

    /**
     * 加载服务
     */
    @PostConstruct
    public void addDynamicRules() {
        // 加载限流配置
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(serverAddress,
                NacosPropertiesConstant.FLOW_RULE_GROUP_ID, NacosPropertiesConstant.FLOW_RULE_DATA_ID,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() { }));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
        // 加载降级配置
        ReadableDataSource<String, List<DegradeRule>> degradeRuleDataSource = new NacosDataSource<>(serverAddress,
                NacosPropertiesConstant.DEGRADE_RULE_GROUP_ID, NacosPropertiesConstant.DEGRADE_RULE_DATA_ID,
                source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() { }));
        DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());
    }
}
