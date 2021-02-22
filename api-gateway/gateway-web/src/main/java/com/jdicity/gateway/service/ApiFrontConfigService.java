package com.jdicity.gateway.service;

import com.jdicity.gateway.entity.ApiFrontConfig;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/17 20:43
 */

public interface ApiFrontConfigService {
    ApiFrontConfig addApiFrontConfig(ApiFrontConfig apiFrontConfig);

    int deleteApiFrontConfig(ApiFrontConfig apiFrontConfig);

    int updateApiFrontConfig(ApiFrontConfig apiFrontConfig);

    ApiFrontConfig findApiFrontConfigById(Long apiFrontConfigId);
}
