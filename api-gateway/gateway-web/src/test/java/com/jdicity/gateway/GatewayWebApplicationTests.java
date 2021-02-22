package com.jdicity.gateway;

import com.jdicity.gateway.dto.Authority;
import com.jdicity.gateway.entity.ApiForDatabase;
import com.jdicity.gateway.entity.CfgForDatabase;
import com.jdicity.gateway.mapper.*;
import com.jdicity.gateway.nacos.DatabaseToNacos;
import com.jdicity.gateway.sentinel.DegradeRuleToNacos;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@Disabled
@ActiveProfiles("integrationTest")
@SpringBootTest
class GatewayWebApplicationTests {
    @Autowired
    private ApiForDatabaseMapper apiForDatabaseMapper;

    @Autowired
    private ApiInfoMapper apiInfoMapper;

    @Autowired
    private DatabaseToNacos databaseToNacos;

    @Autowired
    private SentinelForDatabaseMapper sentinelForDatabaseMapper;

    @Autowired
    private DegradeRuleToNacos degradeRuleToNacos;

    @Autowired
    private AuthorityMapper authorityMapper;

    @Autowired
    private CfgForDatabaseMapper cfgForDatabaseMapper;

    @Test
    public void testSql() {
        ApiForDatabase content = apiForDatabaseMapper.findById(3);
        System.out.println(content);
    }

    @Test
    public void testApiForNacos(){
        String gatewayContent = databaseToNacos.getGatewayContent(3);
        System.out.println(gatewayContent);
    }

    @Test
    public void testApiInfo(){
        List<Long> allIds = apiInfoMapper.findAllIds();
        System.out.println(allIds);
    }

    @Test
    public void testSentinelForDatabase(){
//        SentinelForDatabase ruleByApiId = sentinelForDatabaseMapper.findRuleByApiId(3);
//        System.out.println(ruleByApiId);
        String degradeRules = degradeRuleToNacos.getDegradeRules();
        System.out.println(degradeRules);
    }

    @Test
    public void testAuthorityMapper(){
        Authority authorityByAppId = authorityMapper.findAuthorityByAppId(3L);
        System.out.println(authorityByAppId);
    }

    @Test
    public void testCfgForDatabaseMapper(){
        List<CfgForDatabase> cfgInfo = cfgForDatabaseMapper.findCfgInfo();
        System.out.println(cfgInfo);
    }

}
