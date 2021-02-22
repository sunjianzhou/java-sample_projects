package com.jdicity.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdicity.gateway.entity.ConstantParameter;
import com.jdicity.gateway.mapper.ConstantParameterMapper;
import com.jdicity.gateway.service.ConstantParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/18 0:08
 */

@Service
public class ConstantParamServiceImpl implements ConstantParamService {
    @Autowired
    private ConstantParameterMapper constantParameterMapper;

    @Override
    public ConstantParameter addConstantParameter(ConstantParameter constantParameter) {
        constantParameterMapper.insert(constantParameter);
        return constantParameter;
    }

    @Override
    public int deleteConstantParameter(ConstantParameter constantParameter) {
        QueryWrapper<ConstantParameter> qw = new QueryWrapper<>();
        qw.eq("id", constantParameter.getId());
        return constantParameterMapper.delete(qw);
    }

    @Override
    public int updateConstantParameter(ConstantParameter constantParameter) {
        QueryWrapper<ConstantParameter> qw = new QueryWrapper<>();
        qw.eq("id", constantParameter.getId());
        return constantParameterMapper.update(constantParameter, qw);
    }

    @Override
    public ConstantParameter findConstantParameterById(Long constantParamId) {
        return constantParameterMapper.selectById(constantParamId);
    }
}
