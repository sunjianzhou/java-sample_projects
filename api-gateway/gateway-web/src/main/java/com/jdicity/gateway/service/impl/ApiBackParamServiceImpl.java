package com.jdicity.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdicity.gateway.entity.ApiBackParameter;
import com.jdicity.gateway.mapper.ApiBackParameterMapper;
import com.jdicity.gateway.service.ApiBackParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/17 23:31
 */

@Service
public class ApiBackParamServiceImpl implements ApiBackParamService {
    @Autowired
    private ApiBackParameterMapper apiBackParameterMapper;

    @Override
    public ApiBackParameter addApiBackParameter(ApiBackParameter apiBackParameter) {
        apiBackParameterMapper.insert(apiBackParameter);
        return apiBackParameter;
    }

    @Override
    public int deleteApiBackParameter(ApiBackParameter apiBackParameter) {
        QueryWrapper<ApiBackParameter> qw = new QueryWrapper<>();
        qw.eq("id", apiBackParameter.getId());
        return apiBackParameterMapper.delete(qw);
    }

    @Override
    public int updateApiBackParameter(ApiBackParameter apiBackParameter) {
        QueryWrapper<ApiBackParameter> qw = new QueryWrapper<>();
        qw.eq("id", apiBackParameter.getId());
        return apiBackParameterMapper.update(apiBackParameter, qw);
    }

    @Override
    public ApiBackParameter findApiBackParameterById(Long id) {
        return apiBackParameterMapper.selectById(id);
    }
}
