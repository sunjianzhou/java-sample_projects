package com.jdicity.gateway;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.jdicity.gateway.exceptions.CustomErrorException;
import com.jdicity.gateway.filters.AccessTokenCheckFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.SocketUtils;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.Duration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2021/1/9 0:25
 */

@ActiveProfiles("integrationTest")
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = "management.server.port=${test.port}")
public class AccessTokenCheckFilterTest {
    private WireMockServer wireMockServer;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    @Mock
    private GatewayFilterChain filterChain;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private AccessTokenCheckFilter accessTokenCheckFilter;

    @BeforeAll
    static void beforeClass() {
        int managementPort = SocketUtils.findAvailableTcpPort();
        System.setProperty("test.port", String.valueOf(managementPort));

    }

    @BeforeEach
    void setup() throws Exception {
        int mockServerPort = 8090;
        wireMockServer = new WireMockServer(mockServerPort);
        wireMockServer.start();
        setupStub();
        String definitionString = String.format("{\"id\":\"web\",\"predicates\":" +
                        "[{\"args\":{\"pattern\":\"/hi\"},\"name\":\"Path\"}],\"filters\":" +
                        "[{\"name\":\"SetPath\",\"args\":{\"template\":\"/test\"}},{\"name\":\"AccessTokenCheck\"}]," +
                        "\"uri\":\"http://localhost:%d/\"}",
                mockServerPort);

        RouteDefinition routeDefinition = objectMapper.readValue(definitionString, new TypeReference<RouteDefinition>() {
        });
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        publisher.publishEvent(new RefreshRoutesEvent(this.routeDefinitionWriter));
    }

    private void setupStub() {
        wireMockServer.stubFor(get(urlEqualTo("/test"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"foo\":\"bar\"}")
                        .withHeader("Content-Type", "application/json")));
    }

    @AfterEach
    void teardown() {
        wireMockServer.stop();
    }

    @Test
    void testAccessTokenCheck_whenTokenIsIllegal_thenThrowException() {
        MockServerHttpRequest expected = MockServerHttpRequest.get("/hi")
                .header("clientId", "testClientId").header("accessToken", "testAccessToken").build();
        MockServerWebExchange exchange = MockServerWebExchange.from(expected);
        Exception exception = assertThrows(CustomErrorException.class, () ->
                accessTokenCheckFilter.filter(exchange, filterChain)
        );
        assertThat(exception.getMessage()).isEqualTo("accessToken错误。");
    }
}
