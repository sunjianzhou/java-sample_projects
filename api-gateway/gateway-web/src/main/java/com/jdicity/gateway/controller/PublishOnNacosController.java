package com.jdicity.gateway.controller;

import com.jdicity.gateway.service.PublishOnNacosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/20 16:23
 */

@RestController
@RequestMapping("/publish")
@Api(value = "发布API接口", tags = "发布API接口")
public class PublishOnNacosController {
    @Autowired
    private PublishOnNacosService publishOnNacosService;

    @PostMapping("/gateway")
    @ApiOperation(value = "发布路由规则")
    public String publishGateway() {
        return publishOnNacosService.publishGateway();
    }

    @PostMapping("/flowRule")
    @ApiOperation(value = "发布限流规则")
    public String publishFlowRule() {
        return publishOnNacosService.publishFlowRule();
    }

    @PostMapping("/degradeRule")
    @ApiOperation(value = "发布降级规则")
    public String publishDegradeRule() {
        return publishOnNacosService.publishDegradeRule();
    }

    @PostMapping("/authority")
    @ApiOperation(value = "发布权限映射")
    public String publishAuthority() {
        return publishOnNacosService.publishAuthority();
    }
}
