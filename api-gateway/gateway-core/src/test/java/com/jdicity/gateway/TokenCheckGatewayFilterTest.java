package com.jdicity.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdicity.gateway.exceptions.CustomErrorException;
import com.jdicity.gateway.filters.TokenCheckGatewayFilter;
import com.jdicity.gateway.util.JWTUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Write description here.
 *
 * @author zhengweibing3
 * @date 2020/12/17/0017 10:17
 */
@ActiveProfiles("integrationTest")
@SpringBootTest
public class TokenCheckGatewayFilterTest {

    @Mock
    private GatewayFilterChain filterChain;

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init() throws JsonProcessingException {
        String definitionString = "{\"filters\":[{\"args\":{\"parts\":\"1\"},\"name\":\"TokenCheck\"}],\"id\":\"web\",\"predicates\":[{\"args\":{\"pattern\":\"/*\"},\"name\":\"Path\"}],\"uri\":\"http://localhost:11009/\"}";

        RouteDefinition routeDefinition = objectMapper.readValue(definitionString, new TypeReference<RouteDefinition>() {});
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();

    }

//    @Test
    void testTokenFilter_thenGetHeaderInfo() {
        String APP_KEY = "app_key";
        String TOKEN = JWTUtils.generateJsonWebToken(APP_KEY);
        MockServerHttpRequest expected = MockServerHttpRequest.post("/v1/profile").header("token", TOKEN).build();
        MockServerWebExchange exchange = MockServerWebExchange.from(expected);
        TokenCheckGatewayFilter tokenCheckGatewayFilter = new TokenCheckGatewayFilter();
        tokenCheckGatewayFilter.filter(exchange, filterChain);
        ServerHttpRequest actual = exchange.getRequest();
        assertThat(Objects.equals(actual.getHeaders().getFirst(APP_KEY), APP_KEY));
    }

    @Test
    void testTokenFilter_whenTokenIsIllegal_thenThrowException() {
        String TOKEN = "aaa";
        MockServerHttpRequest expected = MockServerHttpRequest.post("/v1/profile").header("token", TOKEN).build();
        MockServerWebExchange exchange = MockServerWebExchange.from(expected);
        TokenCheckGatewayFilter tokenCheckGatewayFilter = new TokenCheckGatewayFilter();
        RuntimeException exception = assertThrows(CustomErrorException.class, () ->
                tokenCheckGatewayFilter.filter(exchange, filterChain)
        );
        assertThat(exception.getMessage()).isEqualTo("token非法");
    }
}
