package com.jdicity.gateway.entity;

import lombok.Data;

import java.util.List;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/29 10:59
 */

@Data
public class CfgForDatabase {
    private String gatewayId;

    private String uri;

    private String predicate;

    private String regularPath;

    private String actualPath;

    private String partsKey;

    private List<String> filters;
}
