package com.jdicity.gateway.service;

import com.jdicity.gateway.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2020/12/29 15:11
 */
@Slf4j
@Service
public class NonceCheckService {

    private final String nonceCacheKey = "gateway_nonce_";

    private final Long nonceCacheSeconds = 300L;

    @Resource
    CacheService cacheService;

    public boolean checkNonce(String nonce) {

        if (StringUtils.isEmpty(nonce)) {
            return false;
        }
        Object requestCache = cacheService.get(nonceCacheKey + nonce);
        if (requestCache != null) {
            return false;
        } else {
            cacheService.set(nonceCacheKey + nonce, "", nonceCacheSeconds);
        }
        return true;
    }

}
