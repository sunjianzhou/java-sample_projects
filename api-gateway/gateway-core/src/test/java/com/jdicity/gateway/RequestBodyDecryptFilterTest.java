package com.jdicity.gateway;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.jdicity.gateway.service.RsaEncryptService;
import com.jdicity.gateway.service.SignCheckService;
import com.jdicity.gateway.util.AESUtils;
import com.jdicity.gateway.util.RSAUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import javax.crypto.Cipher;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2021/1/8 20:29
 */

@Slf4j
@ActiveProfiles("integrationTest")
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = "management.server.port=${test.port}")
public class RequestBodyDecryptFilterTest {
    private WireMockServer wireMockServer;

    private WebTestClient webClient;

    @LocalServerPort
    protected int port = 0;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    @Resource
    private SignCheckService signCheckService;

    private String publicKeyString =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDD/3FcVm/T/tpizE6Gn8v5cWyr" +
                    "QQoub86xBT8cUtrb77jlmoYAlCT+rD5LNibZZ9aH8CegxhTyEc2bDzKO4srvneaV" +
                    "cqFPktPFm/X1f8SS/PBzyJbwCabJzJbPCj1IsKCAO5ipK1IuQVpgKWmhA/86KnUg" +
                    "M+trkPJ8BFuGJ3vAAwIDAQAB";

    private String privateKeyString =
            "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMP/cVxWb9P+2mLM" +
                    "Toafy/lxbKtBCi5vzrEFPxxS2tvvuOWahgCUJP6sPks2Jtln1ofwJ6DGFPIRzZsP" +
                    "Mo7iyu+d5pVyoU+S08Wb9fV/xJL88HPIlvAJpsnMls8KPUiwoIA7mKkrUi5BWmAp" +
                    "aaED/zoqdSAz62uQ8nwEW4Yne8ADAgMBAAECgYEAkLMF/iXSabC9ijNLxhgzfvU+" +
                    "RTC/U4k4D3jQHxkMe1OALgLm64ZzyZFgbCaOh/MhbzreBtQ2ooCCSvftW5AUQYbF" +
                    "YAB5pmTqT4V8PnxKeZmPGfkP0rE6EOzMuK6GhNKYSZlaml2xPG9/IQmGF5v0TkRP" +
                    "CgsxkQ7hODeaaUnI+AECQQD4nn9bjLZ3p8lw5Ptw6yQbae2QmZwdRv+1ht7kjc4K" +
                    "utOaaVKG7Gvhyi53kpeTulQRxLxgbUGzx6/jkOYb+D1lAkEAydEGJNQNxRKmPk4/" +
                    "LUczbljgP+U5Kp0RrD0djEr5f9E1K9RQP8PCMzoYlzhip72QdqvpcadTgypr09NB" +
                    "YJTFRwJAZ38DoYbYRsNwVjDcg0s/wrG8FZ/8nyt5M4Yrr5VgmpLfl74UftpYpqvV" +
                    "4C0EMJk2ehceHD4fRcnw7JjbUfVrCQJBAKo1kTH0apygavc3dQ8R8u2JJbCd+gXX" +
                    "rPUQImCVRzIm1updSUVOK/aac+zuED6aoUGFIgDJ96QzXfesP4JeVrcCQBeKVI1G" +
                    "gk1Pjru/Pi0vPcasqJjBXLh+Hal8l7vL0fv0cvzwJ4irHSo2N0CFPpm1jGK6h2yx" +
                    "507SDRM0mimjNyQ=";

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private RsaEncryptService rsaEncryptService;


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
                        "[{\"name\":\"SetPath\",\"args\":{\"template\":\"/test\"}},{\"name\":\"RequestBodyDecrypt\"}]," +
                        "\"uri\":\"http://localhost:%d/\"}",
                mockServerPort);

        RouteDefinition routeDefinition = objectMapper.readValue(definitionString, new TypeReference<RouteDefinition>() {
        });
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        publisher.publishEvent(new RefreshRoutesEvent(this.routeDefinitionWriter));
    }

    private void setupStub() {
        wireMockServer.stubFor(post(urlEqualTo("/test"))
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
    void testRequestBodyDecrypt_whenRequest_thenDecryptRequestBody() throws Exception {
        String body = "{\"request\":\"hello\"}";
        String aesKey = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
        String bodyEncrypt = AESUtils.aesEncrypt(body, aesKey);
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("data", bodyEncrypt);
        String bodyStr = new ObjectMapper().writeValueAsString(bodyMap);

        RSAUtils rsaUtils = new RSAUtils();
        PublicKey publicKey = RSAUtils.getPublicKey(publicKeyString);
        String aesKeyEncrypt = rsaUtils.encryptWithBase64(publicKey, aesKey);

        webClient.post().uri("/hi").bodyValue(bodyStr).header("aesKey", aesKeyEncrypt)
                .exchange().expectStatus().isOk()
                .expectBody()
                .jsonPath("$.foo").isEqualTo("bar");
    }
}
