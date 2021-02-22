package com.jdicity.gateway.service;

import com.jdicity.gateway.entity.ApiBackConfig;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date     
 */



public interface ApiBackConfigService {
    ApiBackConfig addApiBackConfig(ApiBackConfig apiBackConfig);

    int deleteApiBackConfig(ApiBackConfig apiBackConfig);

    int updateApiBackConfig(ApiBackConfig apiBackConfig);

    ApiBackConfig findApiBackConfigById(Long apiBackConfigId);
}
