package com.jdicity.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdicity.gateway.entity.Application;
import com.jdicity.gateway.mapper.ApplicationMapper;
import com.jdicity.gateway.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/18 10:39
 */

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationMapper applicationMapper;

    @Override
    public Application addApplication(Application application) {
        applicationMapper.insert(application);
        return application;
    }

    @Override
    public int deleteApplication(Application application) {
        QueryWrapper<Application> qw = new QueryWrapper<>();
        qw.eq("id", application.getId());
        return applicationMapper.delete(qw);
    }

    @Override
    public int updateApplication(Application application) {
        QueryWrapper<Application> qw = new QueryWrapper<>();
        qw.eq("id", application.getId());
        return applicationMapper.update(application, qw);
    }

    @Override
    public Application findApplicationById(Long id) {
        return applicationMapper.selectById(id);
    }
}
