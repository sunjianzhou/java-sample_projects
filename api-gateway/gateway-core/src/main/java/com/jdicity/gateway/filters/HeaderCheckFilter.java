package com.jdicity.gateway.filters;

import com.jdicity.gateway.cache.CacheService;
import com.jdicity.gateway.service.NonceCheckService;
import com.jdicity.gateway.service.TimeCheckService;
import org.apache.commons.lang3.StringUtils;
import com.jdicity.gateway.constant.CacheKeys;
import com.jdicity.gateway.constant.HeaderEnum;
import com.jdicity.gateway.constant.ResponseCodeEnum;
import com.jdicity.gateway.dto.AppReference;
import com.jdicity.gateway.dto.Authority;
import com.jdicity.gateway.exceptions.CustomErrorException;
import com.jdicity.gateway.filters.constant.FilterOrderEnum;
import com.jdicity.gateway.service.ParamCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 提前获取相关信息，并存入至上下文中。
 *
 * @author sunjianzhou
 * @date 2020/12/18 15:44
 */
@Slf4j
@Component
public class HeaderCheckFilter implements GatewayFilter, Ordered {

    @Resource
    private ParamCheckService paramCheckService;

    @Resource
    private CacheService cacheService;

    @Resource
    private TimeCheckService timeCheckService;

    @Resource
    private NonceCheckService nonceCheckService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();

        // appKey 检查
        String appKey = headers.getFirst(HeaderEnum.APP_KEY.getValue());
        if (StringUtils.isEmpty(appKey) || ((Map) cacheService.get(CacheKeys.CACHE_AUTH_KEY)).get(appKey) == null) {
            String errorMessage = String.format("%s不存在或缺失", "appKey");
            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        // For app check.
        Object object = cacheService.get(CacheKeys.CACHE_AUTH_KEY);
        if (object == null) {
            throw new CustomErrorException("1000", "当前appId不可用");
        }
        Map<String, Authority> map = (Map<String, Authority>) object;
        AppReference appReference = map.get(appKey).getAppReference();
        if (appReference == null) {
            log.info("No app info can be found.");
            return chain.filter(exchange);
        }

        //clientId检查
        String clientId = headers.getFirst(HeaderEnum.CLIENT_ID.getValue());
        if (StringUtils.isEmpty(clientId)) {
            throw new CustomErrorException(ResponseCodeEnum.CLIENT_ID_ERROR);
        }

        //校验appCode
        String appCode = headers.getFirst(HeaderEnum.APP_CODE.getValue());
        if (StringUtils.isEmpty(appCode)) {
            throw new CustomErrorException(ResponseCodeEnum.APP_CODE_ERROR);
        }

        //检查参数完整性
        if ("Y".equals(appReference.getParamCompleteCheck())) {
            String headerParam = paramCheckService.checkParamFromHeader(headers);
            if (StringUtils.isEmpty(headerParam)) {
                String errorMessage = String.format("%s不能为空", headerParam);
                log.error(errorMessage);
                throw new CustomErrorException(ResponseCodeEnum.REQUIRED_PARAMS_MISSING_ERROR);
            }
        }

        // 检查时间戳
        if ("Y".equals(appReference.getParamTsCheck())) {
            String ts = headers.getFirst(HeaderEnum.TS.getValue());
            if (!timeCheckService.checkTimeStamp(ts)) {
                String errorMessage = String.format("请求时间戳超时%s", ts);
                log.error(errorMessage);
                throw new CustomErrorException(ResponseCodeEnum.REQUEST_TIMESTAMP_TIMEOUT);
            }
        }

        //检查随机字符串
        if ("Y".equals(appReference.getParamNonceCheck())) {
            String requestId = headers.getFirst(HeaderEnum.REQUEST_ID.getValue());
            if (!nonceCheckService.checkNonce(requestId)) {
                String errorMessage = String.format("重复请求%s", requestId);
                log.error(errorMessage);
                throw new CustomErrorException(ResponseCodeEnum.NONCE_ERROR);
            }
        }

        //secret检验
        if ("Y".equals(appReference.getAccessTokenCheck())) {
            String secret = headers.getFirst(HeaderEnum.SECRET.getValue());
            if (StringUtils.isEmpty(secret)) {
                throw new CustomErrorException(ResponseCodeEnum.SECRET_MISSING);
            }
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return FilterOrderEnum.HEADER_CHECK.getOrder();
    }
}
