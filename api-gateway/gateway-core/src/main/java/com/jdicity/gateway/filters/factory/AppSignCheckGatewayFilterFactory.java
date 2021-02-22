package com.jdicity.gateway.filters.factory;

import com.jdicity.gateway.filters.common.Decorator;
import com.jdicity.gateway.filters.common.CachedBodyOutputMessage2;
import com.jdicity.gateway.filters.common.FactoryConfig;
import com.jdicity.gateway.filters.constant.FilterOrderEnum;
import com.jdicity.gateway.filters.function.AppSignCheckFunction;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

/**
 * App Sign Check Gateway Filter Factory.
 *
 * @author sunjianzhou
 * @date 2020/12/20 10:37
 */
@Component
public class AppSignCheckGatewayFilterFactory extends AbstractGatewayFilterFactory<FactoryConfig> {

    @Resource
    AppSignCheckFunction appSignCheckFunction;

    private final List<HttpMessageReader<?>> messageReaders;

    public AppSignCheckGatewayFilterFactory() {
        super(FactoryConfig.class);
        this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
    }

    @Override
    public GatewayFilter apply(FactoryConfig config) {
        config.setContentType(MediaType.APPLICATION_JSON_VALUE);
        config.setInClass(String.class);
        config.setOutClass(String.class);
        config.setRewriteFunction(appSignCheckFunction);

        return new AppSignCheckGatewayFilterFactory.ModifyRequestGatewayFilter(config);
    }

    /**
     * App端签名过滤器。
     */
    public class ModifyRequestGatewayFilter implements GatewayFilter, Ordered {
        private final FactoryConfig config;

        public ModifyRequestGatewayFilter(FactoryConfig config) {
            this.config = config;
        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            Class inClass = config.getInClass();
            ServerRequest serverRequest = ServerRequest.create(exchange, AppSignCheckGatewayFilterFactory.this.messageReaders);
            Mono<?> modifiedBody = serverRequest.bodyToMono(inClass)
                    .flatMap(o -> config.getRewriteFunction().apply(exchange, o));
            BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, config.getOutClass());
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(exchange.getRequest().getHeaders());
            headers.remove("Content-Length");
            if (config.getContentType() != null) {
                headers.set("Content-Type", config.getContentType());
            }

            CachedBodyOutputMessage2 outputMessage = new CachedBodyOutputMessage2(exchange, headers);
            return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
                ServerHttpRequest decorator = new Decorator(exchange, headers, outputMessage).decorate();
                return chain.filter(exchange.mutate().request(decorator).build());
            }));
        }

        @Override
        public int getOrder() {
            return FilterOrderEnum.APP_SIGN_CHECK.getOrder();
        }
    }
}
