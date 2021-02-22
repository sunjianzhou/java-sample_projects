package com.jdicity.gateway.filters.function;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdicity.gateway.cache.CacheService;
import com.jdicity.gateway.constant.CacheKeys;
import com.jdicity.gateway.constant.HeaderEnum;
import com.jdicity.gateway.constant.ResponseCodeEnum;
import com.jdicity.gateway.constant.TokenProperties;
import com.jdicity.gateway.dto.Authority;
import com.jdicity.gateway.exceptions.CustomErrorException;
import com.jdicity.gateway.service.SignCheckService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Sign check for app.
 *
 * @author sunjianzhou
 * @date 2020/12/18 16:42
 */
@Slf4j
@Component
public class AppSignCheckFunction implements RewriteFunction<String, String> {

    @Resource
    private SignCheckService signCheckService;

    @Resource
    private CacheService cacheService;

    @SneakyThrows
    @Override
    public Publisher<String> apply(ServerWebExchange exchange, String body) {
        log.info("=================AppSignCheckFilter=================");
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String appKey = headers.getFirst(TokenProperties.HEADER_KEY_APP_KEY);
        Object object = cacheService.get(CacheKeys.CACHE_AUTH_KEY);
        String publicKey = "";
        if (object != null) {
            publicKey = ((Map<String, Authority>) object).get(appKey).getPubKey();
        }
        String sign = headers.getFirst(HeaderEnum.SIGN.getValue());

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> bodyMap = mapper.readValue(body, Map.class);
        String data = signCheckService.getNeedSignDataFromHeaderAndBody(headers, bodyMap);

        log.info("待签名内容：{}", data);
        log.info("客户端签名结果：{}", sign);
        log.info("本地签名结果：{}", signCheckService.sign(data));

        // 公钥解密，同时验签。
        boolean signCheckResult = signCheckService.verifySign(sign, publicKey, data);
        if (!signCheckResult) {
            log.info("验签失败。");
            throw new CustomErrorException(ResponseCodeEnum.SIGN_ERROR);
        }

        return Mono.just(body);
    }
}
