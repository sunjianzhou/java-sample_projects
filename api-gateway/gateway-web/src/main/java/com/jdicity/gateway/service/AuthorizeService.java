package com.jdicity.gateway.service;

import com.jdicity.gateway.entity.ApiApp;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date     
 */



public interface AuthorizeService {
    ApiApp addApiApp(ApiApp apiApp);

    int deleteApiApp(ApiApp apiApp);

    int updateApiApp(ApiApp apiApp);

    ApiApp findApiAppById(Long id);
}
