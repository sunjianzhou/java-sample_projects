package com.jdicity.gateway.service.impl;

import com.jdicity.gateway.authority.AuthorityToNacos;
import com.jdicity.gateway.constant.NacosPropertiesConstant;
import com.jdicity.gateway.nacos.DatabaseToNacos;
import com.jdicity.gateway.sentinel.DegradeRuleToNacos;
import com.jdicity.gateway.sentinel.FlowRuleToNacos;
import com.jdicity.gateway.service.PublishOnNacosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/25 16:44
 */

@Service
public class PublishOnNacosServiceImpl implements PublishOnNacosService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DatabaseToNacos databaseToNacos;

    @Autowired
    private FlowRuleToNacos flowRuleToNacos;

    @Autowired
    private DegradeRuleToNacos degradeRuleToNacos;

    @Autowired
    private AuthorityToNacos authorityToNacos;

    /**
     * Nacos服务中心地址
     */
    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;

    @Override
    public String publishGateway() {
        String url = "http://" + serverAddr + "/nacos/v1/cs/configs";
        String gatewayContent = databaseToNacos.getGatewayContents();

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("dataId", NacosPropertiesConstant.GATEWAY_RULE_DATA_ID);
        param.add("group", NacosPropertiesConstant.GATEWAY_RULE_GROUP_ID);
        param.add("content", gatewayContent);

        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(url, param, String.class);
            if (responseEntity.getBody() == null || "false".equals(responseEntity.getBody())) {
                return "发布路由信息失败。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "发布路由信息失败。";
        }
        return "发布路由信息成功。";
    }

    @Override
    public String publishFlowRule() {
        String url = "http://" + serverAddr + "/nacos/v1/cs/configs";
        String flowRules = flowRuleToNacos.getFlowRules();

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("dataId", NacosPropertiesConstant.FLOW_RULE_DATA_ID);
        param.add("group", NacosPropertiesConstant.FLOW_RULE_GROUP_ID);
        param.add("content", flowRules);

        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(url, param, String.class);
            if (responseEntity.getBody() == null || "false".equals(responseEntity.getBody())) {
                return "发布限流规则失败。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "发布限流规则失败。";
        }
        return "发布限流规则成功。";
    }

    @Override
    public String publishDegradeRule() {
        String url = "http://" + serverAddr + "/nacos/v1/cs/configs";
        String degradeRules = degradeRuleToNacos.getDegradeRules();

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("dataId", NacosPropertiesConstant.DEGRADE_RULE_DATA_ID);
        param.add("group", NacosPropertiesConstant.DEGRADE_RULE_GROUP_ID);
        param.add("content", degradeRules);

        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(url, param, String.class);
            if (responseEntity.getBody() == null || "false".equals(responseEntity.getBody())) {
                return "发布降级规则失败。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "发布降级规则失败。";
        }
        return "发布降级规则成功。";
    }

    @Override
    public String publishAuthority() {
        String url = "http://" + serverAddr + "/nacos/v1/cs/configs";
        String authorities = authorityToNacos.getAuthorities();

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("dataId", NacosPropertiesConstant.AUTH_DATA_ID);
        param.add("group", NacosPropertiesConstant.AUTH_GROUP_ID);
        param.add("content", authorities);

        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(url, param, String.class);
            if (responseEntity.getBody() == null || "false".equals(responseEntity.getBody())) {
                return "发布权限规则失败。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "发布权限规则失败。";
        }
        return "发布权限规则成功。";
    }
}
