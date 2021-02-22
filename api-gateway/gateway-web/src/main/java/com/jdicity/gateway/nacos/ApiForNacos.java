package com.jdicity.gateway.nacos;

import lombok.Data;

import java.util.List;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/20 15:25
 */

@Data
public class ApiForNacos {
    private String id;

    private String uri;

    private List<MyPredicate> predicates;

    private List<MyFilter> filters;
}
