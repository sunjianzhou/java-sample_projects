package com.jdicity.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdicity.gateway.entity.Sentinel;
import com.jdicity.gateway.mapper.SentinelMapper;
import com.jdicity.gateway.service.SentinelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/23 20:32
 */

@Service
public class SentinelServiceImpl implements SentinelService {
    @Autowired
    private SentinelMapper sentinelMapper;

    @Override
    public Sentinel addSentinel(Sentinel sentinel) {
        sentinelMapper.insert(sentinel);
        return sentinel;
    }

    @Override
    public int deleteSentinel(Sentinel sentinel) {
        QueryWrapper<Sentinel> qw = new QueryWrapper<>();
        qw.eq("id", sentinel.getId());
        return sentinelMapper.delete(qw);
    }

    @Override
    public int updateSentinel(Sentinel sentinel) {
        QueryWrapper<Sentinel> qw = new QueryWrapper<>();
        qw.eq("id", sentinel.getId());
        return sentinelMapper.update(sentinel, qw);
    }

    @Override
    public Sentinel findSentinelById(Long sentinelId) {
        return sentinelMapper.selectById(sentinelId);
    }
}
