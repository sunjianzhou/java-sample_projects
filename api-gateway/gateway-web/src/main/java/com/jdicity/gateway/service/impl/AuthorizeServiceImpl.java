package com.jdicity.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdicity.gateway.entity.ApiApp;
import com.jdicity.gateway.mapper.ApiAppMapper;
import com.jdicity.gateway.service.AuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/18 11:03
 */

@Service
public class AuthorizeServiceImpl implements AuthorizeService {
    @Autowired
    private ApiAppMapper apiAppMapper;

    @Override
    public ApiApp addApiApp(ApiApp apiApp) {
        apiAppMapper.insert(apiApp);
        return apiApp;
    }

    @Override
    public int deleteApiApp(ApiApp apiApp) {
        QueryWrapper<ApiApp> qw = new QueryWrapper<>();
        qw.eq("id", apiApp.getId());
        return apiAppMapper.delete(qw);
    }

    @Override
    public int updateApiApp(ApiApp apiApp) {
        QueryWrapper<ApiApp> qw = new QueryWrapper<>();
        qw.eq("id", apiApp.getId());
        return apiAppMapper.update(apiApp, qw);
    }

    @Override
    public ApiApp findApiAppById(Long id) {
        return apiAppMapper.selectById(id);
    }
}
