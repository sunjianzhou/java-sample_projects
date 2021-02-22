package com.jdicity.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdicity.gateway.entity.ServiceGroup;
import com.jdicity.gateway.mapper.ServiceMapper;
import com.jdicity.gateway.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/17 11:18
 */

@org.springframework.stereotype.Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private ServiceMapper serviceMapper;

    @Override
    public ServiceGroup addGroup(ServiceGroup group) {
        serviceMapper.insert(group);
        return group;
    }

    @Override
    public int deleteGroup(ServiceGroup group) {
        QueryWrapper<ServiceGroup> qw = new QueryWrapper<>();
        qw.eq("id", group.getId());
        return serviceMapper.delete(qw);
    }

    @Override
    public int updateGroup(ServiceGroup group) {
        QueryWrapper<ServiceGroup> qw = new QueryWrapper<>();
        qw.eq("id", group.getId());
        return serviceMapper.update(group, qw);
    }

    @Override
    public ServiceGroup findGroupById(Long serviceId) {
        return serviceMapper.selectById(serviceId);
    }
}
