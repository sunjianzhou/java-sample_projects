package com.jdicity.gateway.service;

import com.jdicity.gateway.entity.Application;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date     
 */



public interface ApplicationService {
    Application addApplication(Application application);

    int deleteApplication(Application application);

    int updateApplication(Application application);

    Application findApplicationById(Long id);
}
