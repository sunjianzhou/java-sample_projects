package com.jdicity.ucuc.interceptor;

import com.jdicity.ucuc.constant.enums.ResponseCodeEnum;
import com.jdicity.ucuc.constant.Cache;
import com.jdicity.ucuc.constant.Common;
import com.jdicity.ucuc.constant.Dright;
import com.jdicity.ucuc.exc.DrightException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 获取天权token拦截器.
 *
 * @author liyingda
 * @date 2020-11-13 16:30
 * @version v1.0.0
 */
@Component
@Slf4j
public class DrightInterceptor extends HandlerInterceptorAdapter {

    /**
     * 天权 baseUrl
     */
    @Value("${dright.baseUrl}")
    private String baseUrl;

    /**
     * 天权 clientGrantType
     */
    @Value("${dright.clientCredentials}")
    private String clientGrantType;

    /**
     * 天权 clientId
     */
    @Value("${dright.clientId}")
    private String clientId;

    /**
     * 天权 clientSecret
     */
    @Value("${dright.clientSecret}")
    private String clientSecret;

    /**
     * 天权 clientUrl
     */
    @Value("${dright.clientUrl}")
    private String clientUrl;

    /**
     * redisTemplate缓存
     */
    @Resource
    RedisTemplate<String, String> redisTemplate;

    /**
     * restTemplate http网络请求
     */
    @Resource
    private RestTemplate restTemplate;

    /**
     * 获取天权token, 并存入缓存.
     *
     * @param request  request
     * @param response response
     * @param handler  handler
     * @return true or false
     * @description Get access token from dright client.
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {
        String authorizationToken = redisTemplate.opsForValue().get(Cache.KEY_DRIGHT_AUTHORIZATION);
        log.info("缓存中的authorizationToken: {}.", authorizationToken);
        if (null == authorizationToken) {
            final String url = baseUrl + clientUrl;

            Map<String, String> map = new HashMap<>(Common.DEFAULT_CAPACITY);
            map.put("Content-Type", "application/x-www-form-urlencoded");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setAll(map);

            LinkedMultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();
            valueMap.add(Dright.REQUEST_KEY_CLIENT_ID, clientId);
            valueMap.add(Dright.REQUEST_KEY_CLIENT_SECRET, clientSecret);
            valueMap.add(Dright.REQUEST_KEY_CLIENT_GRANT, clientGrantType);

            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(
                    valueMap, httpHeaders);
            ResponseEntity<Map> mapResponseEntity = restTemplate.postForEntity(
                    url, entity, Map.class);
            if (mapResponseEntity.getStatusCode().value() != Dright.SUCCESS_CODE) {
                throw new DrightException(ResponseCodeEnum.TOKEN_GET_FAILED);
            }

            Map<String, Object> body = mapResponseEntity.getBody();
            Object code = body.get(Dright.RESPONSE_KEY_CODE);
            if (code instanceof Integer) {
                Integer integer = (Integer) code;
                if (integer != ResponseCodeEnum.SUCCESS.getCode()) {
                    Object msg = body.get(Dright.RESPONSE_KEY_MSG);
                    if (msg instanceof String) {
                        throw new DrightException(
                                ResponseCodeEnum.TOKEN_GET_FAILED.getCode(), (String) msg);
                    }
                }
            }

            Map<String, Object> data = (Map<String, Object>) body.get(Dright.RESPONSE_KEY_DATA);
            Object token = data.get(Dright.RESPONSE_KEY_ACCESS_TOKEN);
            if (token instanceof String) {
                authorizationToken = (String) token;
                log.info("缓存中无authorizationToken, 获取新的token: {}, 过期时间为: {}.",
                        authorizationToken, Cache.EXPIRE_TIME_MIN_720);
                redisTemplate.opsForValue().set(Cache.KEY_DRIGHT_AUTHORIZATION, authorizationToken,
                        Cache.EXPIRE_TIME_MIN_720, TimeUnit.SECONDS);
            }
        }
        return true;
    }
}
