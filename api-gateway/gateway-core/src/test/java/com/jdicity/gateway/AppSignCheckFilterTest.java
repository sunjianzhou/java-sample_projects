package com.jdicity.gateway;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.SocketUtils;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.Duration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2021/1/8 16:21
 */

@ActiveProfiles("integrationTest")
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = "management.server.port=${test.port}")
public class AppSignCheckFilterTest {
    private WireMockServer wireMockServer;

    private WebTestClient webClient;

    @LocalServerPort
    protected int port = 0;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    @Autowired
    private ApplicationEventPublisher publisher;

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
        String baseUri = "http://localhost:" + port;
        this.webClient = WebTestClient.bindToServer().responseTimeout(Duration.ofSeconds(10)).baseUrl(baseUri).build();
        String definitionString = String.format("{\"id\":\"web\",\"predicates\":" +
                        "[{\"args\":{\"pattern\":\"/hi\"},\"name\":\"Path\"}],\"filters\":" +
                        "[{\"name\":\"SetPath\",\"args\":{\"template\":\"/test\"}},{\"name\":\"AppSignCheck\"}]," +
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
    void testSetPathFilter_whenRequest_thenTransferPath() {
        webClient.get().uri("/hi").exchange().expectStatus().isOk()
                .expectBody()
                .jsonPath("$.foo").isEqualTo("bar");
    }
}
