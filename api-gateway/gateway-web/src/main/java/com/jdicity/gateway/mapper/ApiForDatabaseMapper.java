package com.jdicity.gateway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdicity.gateway.entity.ApiForDatabase;
import org.springframework.stereotype.Repository;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/20 12:49
 */
@Repository
public interface ApiForDatabaseMapper extends BaseMapper<ApiForDatabase> {
    ApiForDatabase findById(long apiId);
}
