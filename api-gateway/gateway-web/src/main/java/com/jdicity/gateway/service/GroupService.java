package com.jdicity.gateway.service;

import com.jdicity.gateway.entity.ServiceGroup;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date     
 */



public interface GroupService {
    ServiceGroup addGroup(ServiceGroup group);

    int deleteGroup(ServiceGroup group);

    int updateGroup(ServiceGroup group);

    ServiceGroup findGroupById(Long serviceId);
}
