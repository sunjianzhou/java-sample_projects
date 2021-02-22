package com.jdicity.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdicity.gateway.entity.ApiBackConfig;
import com.jdicity.gateway.mapper.ApiBackConfigMapper;
import com.jdicity.gateway.service.ApiBackConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/17 22:44
 */

@Service
public class ApiBankConfigServiceImpl implements ApiBackConfigService {
    @Autowired
    private ApiBackConfigMapper apiBackConfigMapper;

    @Override
    public ApiBackConfig addApiBackConfig(ApiBackConfig apiBackConfig) {
        apiBackConfigMapper.insert(apiBackConfig);
        return apiBackConfig;
    }

    @Override
    public int deleteApiBackConfig(ApiBackConfig apiBackConfig) {
        QueryWrapper<ApiBackConfig> qw = new QueryWrapper<>();
        qw.eq("id", apiBackConfig.getId());
        return apiBackConfigMapper.delete(qw);
    }

    @Override
    public int updateApiBackConfig(ApiBackConfig apiBackConfig) {
        QueryWrapper<ApiBackConfig> qw = new QueryWrapper<>();
        qw.eq("id", apiBackConfig.getId());
        return apiBackConfigMapper.update(apiBackConfig, qw);
    }

    @Override
    public ApiBackConfig findApiBackConfigById(Long apiBackConfigId) {
        return apiBackConfigMapper.selectById(apiBackConfigId);
    }
}
