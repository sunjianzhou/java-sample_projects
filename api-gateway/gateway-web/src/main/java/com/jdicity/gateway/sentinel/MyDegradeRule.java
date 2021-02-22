package com.jdicity.gateway.sentinel;

import lombok.Data;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/21 16:38
 */

@Data
public class MyDegradeRule {
    private String resource;

    private String grade;

    private Integer count;

    private Integer timeWindow;

    public MyDegradeRule() {
    }

    public MyDegradeRule(String resource, String grade, Integer count, Integer timeWindow) {
        this.resource = resource;
        this.grade = grade;
        this.count = count;
        this.timeWindow = timeWindow;
    }
}
