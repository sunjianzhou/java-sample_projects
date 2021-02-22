package com.jdicity.gateway.sentinel;

import com.google.gson.Gson;
import com.jdicity.gateway.entity.SentinelForDatabase;
import com.jdicity.gateway.mapper.ApiInfoMapper;
import com.jdicity.gateway.mapper.SentinelForDatabaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/21 18:17
 */

@Service
public class FlowRuleToNacos {
    @Autowired
    private SentinelForDatabaseMapper sentinelForDatabaseMapper;

    @Autowired
    private ApiInfoMapper apiInfoMapper;

    public String getFlowRule(long id) {
        SentinelForDatabase flowRule = sentinelForDatabaseMapper.findRuleByApiId(id);
        if (flowRule == null) {
            return null;
        }
        MyFlowRule myFlowRule = new MyFlowRule(flowRule.getFrontPath(), flowRule.getFlowGrade(),
                flowRule.getFlowCount(), flowRule.getFlowStartegy(), flowRule.getFlowBehavior(),
                flowRule.getFlowCluster());
        return new Gson().toJson(myFlowRule);
    }

    public String getFlowRules() {
        List<Long> allIds = apiInfoMapper.findAllIds();
        List<String> flowRules = new ArrayList<>();
        for (Long id : allIds) {
            String flowRule = getFlowRule(id);
            if (flowRule != null) {
                flowRules.add(flowRule);
            }
        }
        return flowRules.toString();
    }
}
