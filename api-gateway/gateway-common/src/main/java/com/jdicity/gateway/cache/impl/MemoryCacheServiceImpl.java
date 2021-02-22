package com.jdicity.gateway.cache.impl;

import com.jdicity.gateway.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2020/12/24 10:50
 */
@Slf4j
@Service
@ConditionalOnExpression("${cache.memory.enabled:true}")
public class MemoryCacheServiceImpl implements CacheService {

    private static final Map<String, Object> MEMORY_CACHE = new HashMap<>();

    @Override
    public Boolean expire(String key, Long time) {
        return true;
    }

    @Override
    public Long getExpire(String key) {
        return -1L;
    }

    @Override
    public Boolean hasKey(String key) {
        try {
            return MEMORY_CACHE.get(key) != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void del(String... keys) {
        for (String each: keys) {
            MEMORY_CACHE.remove(each);
        }
    }

    @Override
    public Object get(String key) {
        return MEMORY_CACHE.get(key);
    }

    @Override
    public Boolean set(String key, Object value) {
        try {
            MEMORY_CACHE.put(key, value);
            return true;
        } catch (Exception e) {
            log.error("内存值设置失败", e);
            return false;
        }
    }

    @Override
    public Boolean set(String key, Object value, Long time) {
        return this.set(key, value);
    }
}
