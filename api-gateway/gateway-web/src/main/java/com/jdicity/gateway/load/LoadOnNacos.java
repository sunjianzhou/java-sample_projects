package com.jdicity.gateway.load;

import com.jdicity.gateway.service.PublishOnNacosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/25 20:39
 */
@Profile("!integrationTest")
@Slf4j
@Component
public class LoadOnNacos implements ApplicationRunner {
    @Autowired
    private PublishOnNacosService publishOnNacosService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String gateway = publishOnNacosService.publishGateway();
        String flowRule = publishOnNacosService.publishFlowRule();
        String degradeRule = publishOnNacosService.publishDegradeRule();
        String authority = publishOnNacosService.publishAuthority();
        log.info("{}", gateway);
        log.info("{}", flowRule);
        log.info("{}", degradeRule);
        log.info("{}", authority);
    }
}
