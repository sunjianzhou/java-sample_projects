package com.jdicity.gateway.service;

import com.jdicity.gateway.entity.ConstantParameter;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date     
 */



public interface ConstantParamService {
    ConstantParameter addConstantParameter(ConstantParameter constantParameter);

    int deleteConstantParameter(ConstantParameter constantParameter);

    int updateConstantParameter(ConstantParameter constantParameter);

    ConstantParameter findConstantParameterById(Long constantParamId);
}
