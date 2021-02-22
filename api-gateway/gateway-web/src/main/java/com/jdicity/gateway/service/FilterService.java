package com.jdicity.gateway.service;

import com.jdicity.gateway.entity.Filter;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date     
 */



public interface FilterService {
    Filter addFilter(Filter filter);

    int deleteFilter(Filter filter);

    int updateFilter(Filter filter);

    Filter findFilterById(Long filterId);
}
