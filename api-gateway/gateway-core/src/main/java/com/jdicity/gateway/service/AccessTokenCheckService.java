package com.jdicity.gateway.service;

import com.jdicity.gateway.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Token 校验。
 *
 * @author sunjianzhou
 * @date 2020/12/21 17:09
 */
@Slf4j
@Service
public class AccessTokenCheckService {

    /**
     * ACCESS_TOKEN
     */
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN_";

    /**
     * ACCESS_TOKEN for app client
     */
    public static final String APP_ACCESS_TOKEN = "JZT_API_ACCESS_TOKEN_";

    /**
     * Old ACCESS_TOKEN for app client
     */
    public static final String APP_ACCESS_TOKEN_OLD = "JZT_API_ACCESS_TOKEN_OLD_";

    @Autowired
    private CacheService cacheService;

    public boolean checkAccessToken(String accessToken, String clientId) {

        Object cacheKeyObj = cacheService.get(ACCESS_TOKEN + clientId);
        Object appCacheKeyObj = cacheService.get(APP_ACCESS_TOKEN + clientId);
        Object oldAppCacheKeyObj = cacheService.get(APP_ACCESS_TOKEN_OLD + clientId);

        String accessTokenCache = String.valueOf(cacheKeyObj);
        String appAccessTokenCache = String.valueOf(appCacheKeyObj);
        String oldAppAccessTokenCache = String.valueOf(oldAppCacheKeyObj);

        boolean hasCacheKey = cacheKeyObj != null && accessTokenCache.equals(accessToken);
        boolean hasAppCacheKey = appCacheKeyObj != null && appAccessTokenCache.equals(accessToken);
        boolean hasAppOldCacheKey = oldAppCacheKeyObj != null && oldAppAccessTokenCache.equals(accessToken);

        return hasCacheKey || hasAppCacheKey || hasAppOldCacheKey;
    }
}
