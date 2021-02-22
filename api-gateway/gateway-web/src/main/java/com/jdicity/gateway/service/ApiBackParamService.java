package com.jdicity.gateway.service;

import com.jdicity.gateway.entity.ApiBackParameter;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date     
 */



public interface ApiBackParamService {
    ApiBackParameter addApiBackParameter(ApiBackParameter apiBackParameter);

    int deleteApiBackParameter(ApiBackParameter apiBackParameter);

    int updateApiBackParameter(ApiBackParameter apiBackParameter);

    ApiBackParameter findApiBackParameterById(Long id);
}
