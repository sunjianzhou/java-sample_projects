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
 * @date 2020/12/21 18:21
 */

@Service
public class DegradeRuleToNacos {
    @Autowired
    private SentinelForDatabaseMapper sentinelForDatabaseMapper;

    @Autowired
    private ApiInfoMapper apiInfoMapper;

    public String getDegradeRule(long id) {
        SentinelForDatabase degradeRule = sentinelForDatabaseMapper.findRuleByApiId(id);
        if (degradeRule == null) {
            return null;
        }
        MyDegradeRule myDegradeRule = new MyDegradeRule(degradeRule.getFrontPath(), degradeRule.getDegradeGrade(),
                degradeRule.getDegradeCount(), degradeRule.getDegradeTime());
        return new Gson().toJson(myDegradeRule);
    }

    public String getDegradeRules() {
        List<Long> allIds = apiInfoMapper.findAllIds();
        List<String> degradeRules = new ArrayList<>();
        for (Long id : allIds) {
            String degradeRule = getDegradeRule(id);
            if (degradeRule != null) {
                degradeRules.add(degradeRule);
            }
        }
        return degradeRules.toString();
    }
}
