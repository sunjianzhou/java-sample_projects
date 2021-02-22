package com.jdicity.gateway.filters.common;

import lombok.AllArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2020/12/20 11:47
 */
@Resource
@AllArgsConstructor
public class Decorator {
    private final ServerWebExchange exchange;
    private final HttpHeaders headers;
    private final CachedBodyOutputMessage2 outputMessage;

    public ServerHttpRequestDecorator decorate() {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                if (contentLength > 0L) {
                    httpHeaders.setContentLength(contentLength);
                } else {
                    httpHeaders.set("Transfer-Encoding", "chunked");
                }

                return httpHeaders;
            }

            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }
}
