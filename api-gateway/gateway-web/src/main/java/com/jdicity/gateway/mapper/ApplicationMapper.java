package com.jdicity.gateway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdicity.gateway.entity.Application;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qixinyuan3
 * @since 2020-12-16
 */
@Repository
public interface ApplicationMapper extends BaseMapper<Application> {
    List<Long> findAllIds();
}
