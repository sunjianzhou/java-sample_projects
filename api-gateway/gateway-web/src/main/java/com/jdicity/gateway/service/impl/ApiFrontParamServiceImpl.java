package com.jdicity.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdicity.gateway.entity.ApiFrontParameter;
import com.jdicity.gateway.mapper.ApiFrontParameterMapper;
import com.jdicity.gateway.service.ApiFrontParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/17 22:05
 */

@Service
public class ApiFrontParamServiceImpl implements ApiFrontParamService {
    @Autowired
    private ApiFrontParameterMapper apiFrontParameterMapper;


    @Override
    public ApiFrontParameter addApiFrontParameter(ApiFrontParameter apiFrontParameter) {
        apiFrontParameterMapper.insert(apiFrontParameter);
        return apiFrontParameter;
    }

    @Override
    public int deleteApiFrontParameter(ApiFrontParameter apiFrontParameter) {
        QueryWrapper<ApiFrontParameter> qw = new QueryWrapper<>();
        qw.eq("id", apiFrontParameter.getId());
        return apiFrontParameterMapper.delete(qw);
    }

    @Override
    public int updateApiFrontParameter(ApiFrontParameter apiFrontParameter) {
        QueryWrapper<ApiFrontParameter> qw = new QueryWrapper<>();
        qw.eq("id", apiFrontParameter.getId());
        return apiFrontParameterMapper.update(apiFrontParameter, qw);
    }

    @Override
    public ApiFrontParameter findApiFrontParameterById(Long frontParameterId) {
        return apiFrontParameterMapper.selectById(frontParameterId);
    }
}
