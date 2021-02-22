package com.jdicity.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdicity.gateway.entity.ApiInfo;
import com.jdicity.gateway.mapper.ApiInfoMapper;
import com.jdicity.gateway.service.ApiInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/17 18:01
 */

@Service
public class ApiInfoServiceImpl implements ApiInfoService {
    @Autowired
    private ApiInfoMapper apiInfoMapper;

    @Override
    public ApiInfo addApiInfo(ApiInfo apiInfo) {
        apiInfoMapper.insert(apiInfo);
        return apiInfo;
    }

    @Override
    public int deleteApiInfo(ApiInfo apiInfo) {
        QueryWrapper<ApiInfo> qw = new QueryWrapper<>();
        qw.eq("id", apiInfo.getId());
        return apiInfoMapper.delete(qw);
    }

    @Override
    public int updateApiInfo(ApiInfo apiInfo) {
        QueryWrapper<ApiInfo> qw = new QueryWrapper<>();
        qw.eq("id", apiInfo.getId());
        return apiInfoMapper.update(apiInfo, qw);
    }

    @Override
    public ApiInfo findApiInfoById(Long apiInfoId) {
        return apiInfoMapper.selectById(apiInfoId);
    }

    @Override
    public List<Long> findAllIds() {
        return apiInfoMapper.findAllIds();
    }
}
