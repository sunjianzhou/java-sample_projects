package com.jdicity.gateway.service;

import com.jdicity.gateway.entity.ApiFrontParameter;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date     
 */



public interface ApiFrontParamService {
    ApiFrontParameter addApiFrontParameter(ApiFrontParameter apiFrontParameter);

    int deleteApiFrontParameter(ApiFrontParameter apiFrontParameter);

    int updateApiFrontParameter(ApiFrontParameter apiFrontParameter);

    ApiFrontParameter findApiFrontParameterById(Long frontParameterId);
}
