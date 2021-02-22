package com.jdicity.gateway.service;

import com.jdicity.gateway.entity.Sentinel;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/23 20:29
 */

public interface SentinelService {
    Sentinel addSentinel(Sentinel sentinel);

    int deleteSentinel(Sentinel sentinel);

    int updateSentinel(Sentinel sentinel);

    Sentinel findSentinelById(Long sentinelId);
}
