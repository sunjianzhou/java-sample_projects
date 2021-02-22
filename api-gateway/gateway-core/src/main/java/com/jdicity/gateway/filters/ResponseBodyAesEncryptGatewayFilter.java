package com.jdicity.gateway.filters;

import com.jdicity.gateway.cache.CacheService;
import com.jdicity.gateway.constant.CacheKeys;
import com.jdicity.gateway.constant.TokenProperties;
import com.jdicity.gateway.dto.Authority;
import com.jdicity.gateway.exceptions.CustomErrorException;
import com.jdicity.gateway.filters.constant.FilterOrderEnum;
import com.jdicity.gateway.service.AesEncryptService;
import com.jdicity.gateway.service.RsaEncryptService;
import com.jdicity.gateway.util.RSAUtils;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Map;
import java.util.UUID;

/**
 * aes加密返回数据.
 *
 * @author zhengweibing3
 * @date 2020/12/27/0027 21:58
 */
@Slf4j
@Component
public class ResponseBodyAesEncryptGatewayFilter implements GatewayFilter, Ordered {

    @Resource
    private CacheService cacheService;

    @Resource
    private AesEncryptService aesEncryptService;

    @Resource
    private RsaEncryptService rsaEncryptService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        DataBufferFactory bufferFactory = response.bufferFactory();
        HttpHeaders headers = request.getHeaders();
        String appKey = headers.getFirst(TokenProperties.HEADER_KEY_APP_KEY);

        Object object = cacheService.get(CacheKeys.CACHE_AUTH_KEY);
        if (object == null) {
            throw new CustomErrorException("1000", "当前appId不可用");
        }
        Map<String, Authority> map = (Map<String, Authority>) object;
        if (map.get(appKey) == null || map.get(appKey).getEncryptPublicKey() == null) {
            throw new CustomErrorException("1000", "当前appId不可用");
        }
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        Authority authority = map.get(appKey);
                        String aesKey = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
                        PublicKey publicKey = null;
                        try {
                            publicKey = RSAUtils.getPublicKey(authority.getEncryptPublicKey());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String rsaEncryptKey = rsaEncryptService.encryptAesKey(publicKey, aesKey);

                        String data = aesEncryptService.encryptData(aesKey, new String(content, StandardCharsets.UTF_8));
                        response.getHeaders().setContentLength(data.getBytes().length);
                        response.getHeaders().set(TokenProperties.HEADER_KEY_AES_KEY, rsaEncryptKey);
                        return bufferFactory.wrap(data.getBytes());
                    }));
                }
                return super.writeWith(body);
            }
        };
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        return FilterOrderEnum.AES_RESPONSE_ENCRYPT.getOrder();
    }
}
