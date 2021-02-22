package com.jdicity.gateway.nacos;

import lombok.Data;

import java.util.Map;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/20 15:27
 */

@Data
public class MyPredicate {
    private String name;

    private Map<String, String> args;
}
