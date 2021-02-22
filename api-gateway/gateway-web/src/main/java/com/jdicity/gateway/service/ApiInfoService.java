package com.jdicity.gateway.service;

import com.jdicity.gateway.entity.ApiInfo;

import java.util.List;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date
 */


public interface ApiInfoService {
    ApiInfo addApiInfo(ApiInfo apiInfo);

    int deleteApiInfo(ApiInfo apiInfo);

    int updateApiInfo(ApiInfo apiInfo);

    ApiInfo findApiInfoById(Long apiInfoId);

    List<Long> findAllIds();

}
