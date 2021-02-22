package com.jdicity.gateway.sentinel;

import lombok.Data;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/21 16:38
 */

@Data
public class MyFlowRule {
    private String resource;

    private String grade;

    private Integer count;

    private String strategy;

    private String controlBehavior;

    private String clusterMode;

    public MyFlowRule(String resource, String grade, Integer count, String strategy, String controlBehavior, String clusterMode) {
        this.resource = resource;
        this.grade = grade;
        this.count = count;
        this.strategy = strategy;
        this.controlBehavior = controlBehavior;
        this.clusterMode = clusterMode;
    }
}
