package com.jdicity.gateway.service;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/25 16:41
 */

public interface PublishOnNacosService {
    String publishGateway();

    String publishFlowRule();

    String publishDegradeRule();

    String publishAuthority();
}
