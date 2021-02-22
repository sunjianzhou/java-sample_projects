package com.jdicity.gateway.entity;

import lombok.Data;

import java.util.List;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/20 12:41
 */

@Data
// @Component
public class ApiForDatabase {
    private Long id;

    private String apiName;

    private String frontPath;

    private String backAddr;

    private String backPath;

    private List<String> filterName;

}
