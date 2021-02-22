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
 * demo gateway test
 *
 * @author zhengweibing3
 * @date 2020/12/23/0023 17:16
 */
@ActiveProfiles("integrationTest")
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = "management.server.port=${test.port}")
class GatewayCoreApplicationTests {

    private WireMockServer wireMockServer;

    @LocalServerPort
    protected int port = 0;

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    @Autowired
    private ApplicationEventPublisher publisher;

    private WebTestClient webClient;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void beforeClass(){
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
                "[{\"args\":{\"pattern\":\"/hi\"},\"name\":\"Path\"}],\"uri\":\"http://localhost:%d/\"}",
                mockServerPort);

        RouteDefinition routeDefinition = objectMapper.readValue(definitionString, new TypeReference<RouteDefinition>() {});
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        publisher.publishEvent(new RefreshRoutesEvent(this.routeDefinitionWriter));
    }

    @AfterEach
    void teardown() {
        wireMockServer.stop();
    }

    private void setupStub() {
        wireMockServer.stubFor(get(urlEqualTo("/hi"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"foo\":\"bar\"}")
                        .withHeader("Content-Type", "application/json")));
    }

    @Test
    void contextLoads() {
    }

    @Test
    void givenMockServer_whenRequest_thenReturnResponse() {
        webClient.get().uri("/hi").exchange().expectStatus().isOk()
                .expectBody()
                .jsonPath("$.foo").isEqualTo("bar");
    }

}
