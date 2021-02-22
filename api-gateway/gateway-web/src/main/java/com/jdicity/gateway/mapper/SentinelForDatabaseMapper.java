package com.jdicity.gateway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdicity.gateway.entity.SentinelForDatabase;
import org.springframework.stereotype.Repository;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/21 17:57
 */
@Repository
public interface SentinelForDatabaseMapper extends BaseMapper<SentinelForDatabase> {
    SentinelForDatabase findRuleByApiId(long apiId);
}
