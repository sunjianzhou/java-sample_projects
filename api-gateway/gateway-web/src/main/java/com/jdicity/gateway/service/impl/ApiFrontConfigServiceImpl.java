package com.jdicity.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdicity.gateway.entity.ApiFrontConfig;
import com.jdicity.gateway.mapper.ApiFrontConfigMapper;
import com.jdicity.gateway.service.ApiFrontConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/17 20:47
 */

@Service
public class ApiFrontConfigServiceImpl implements ApiFrontConfigService {
    @Autowired
    private ApiFrontConfigMapper apiFrontConfigMapper;

    @Override
    public ApiFrontConfig addApiFrontConfig(ApiFrontConfig apiFrontConfig) {
        apiFrontConfigMapper.insert(apiFrontConfig);
        return apiFrontConfig;
    }

    @Override
    public int deleteApiFrontConfig(ApiFrontConfig apiFrontConfig) {
        QueryWrapper<ApiFrontConfig> qw = new QueryWrapper<>();
        qw.eq("id", apiFrontConfig.getId());
        return apiFrontConfigMapper.delete(qw);
    }

    @Override
    public int updateApiFrontConfig(ApiFrontConfig apiFrontConfig) {
        QueryWrapper<ApiFrontConfig> qw = new QueryWrapper<>();
        qw.eq("id", apiFrontConfig.getId());
        return apiFrontConfigMapper.update(apiFrontConfig, qw);
    }

    @Override
    public ApiFrontConfig findApiFrontConfigById(Long apiFrontConfigId) {
        return apiFrontConfigMapper.selectById(apiFrontConfigId);
    }
}
