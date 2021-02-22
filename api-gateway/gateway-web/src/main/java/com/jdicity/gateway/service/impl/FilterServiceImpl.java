package com.jdicity.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdicity.gateway.entity.Filter;
import com.jdicity.gateway.mapper.FilterMapper;
import com.jdicity.gateway.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/18 10:18
 */

@Service
public class FilterServiceImpl implements FilterService {
    @Autowired
    private FilterMapper filterMapper;

    @Override
    public Filter addFilter(Filter filter) {
        filterMapper.insert(filter);
        return filter;
    }

    @Override
    public int deleteFilter(Filter filter) {
        QueryWrapper<Filter> qw = new QueryWrapper<>();
        qw.eq("id", filter.getId());
        return filterMapper.delete(qw);
    }

    @Override
    public int updateFilter(Filter filter) {
        QueryWrapper<Filter> qw = new QueryWrapper<>();
        qw.eq("id", filter.getId());
        return filterMapper.update(filter, qw);
    }

    @Override
    public Filter findFilterById(Long filterId) {
        return filterMapper.selectById(filterId);
    }
}
