package com.jdicity.gateway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdicity.gateway.entity.CfgForDatabase;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/29 11:03
 */
@Repository
public interface CfgForDatabaseMapper extends BaseMapper<CfgForDatabase> {
    List<CfgForDatabase> findCfgInfo();
}
