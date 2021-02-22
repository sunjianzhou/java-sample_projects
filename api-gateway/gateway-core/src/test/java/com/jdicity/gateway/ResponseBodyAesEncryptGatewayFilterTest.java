package com.jdicity.gateway;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.jdicity.gateway.cache.CacheService;
import com.jdicity.gateway.constant.CacheKeys;
import com.jdicity.gateway.constant.TokenProperties;
import com.jdicity.gateway.dto.Authority;
import com.jdicity.gateway.util.AESUtils;
import com.jdicity.gateway.util.RSAUtils;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.SocketUtils;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * response body aes+rsa encrypt test
 *
 * @author zhengweibing3
 * @date 2021/1/8/0008 11:12
 */
@ActiveProfiles("integrationTest")
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = "management.server.port=${test.port}")
public class ResponseBodyAesEncryptGatewayFilterTest {

    private WireMockServer wireMockServer;

    @LocalServerPort
    protected int port = 0;

    private String appKey = "appKey";

    private String responseBodyString = "{\"foo\":\"bar\"}";

    private String encryptPublicKey =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDD/3FcVm/T/tpizE6Gn8v5cWyr" +
            "QQoub86xBT8cUtrb77jlmoYAlCT+rD5LNibZZ9aH8CegxhTyEc2bDzKO4srvneaV" +
            "cqFPktPFm/X1f8SS/PBzyJbwCabJzJbPCj1IsKCAO5ipK1IuQVpgKWmhA/86KnUg" +
            "M+trkPJ8BFuGJ3vAAwIDAQAB";

    private String encryPrivateKey =
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

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Resource
    private CacheService cacheService;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void beforeClass(){
        int managementPort = SocketUtils.findAvailableTcpPort();
        System.setProperty("test.port", String.valueOf(managementPort));
    }

    @BeforeEach
    void setup() throws Exception {
        // mock server
        int mockServerPort = 8090;
        wireMockServer = new WireMockServer(mockServerPort);
        wireMockServer.start();
        setupStub();
        // 添加路由规则
        String definitionString = String.format("{\"filters\":[{\"name\":\"ResponseBodyAesEncrypt\"}],\"id\":\"web\"," +
                        "\"predicates\":" + "[{\"args\":{\"pattern\":\"/hi\"},\"name\":\"Path\"}]," +
                        "\"uri\":\"http://localhost:%d/\"}", mockServerPort);
        RouteDefinition routeDefinition = objectMapper.readValue(definitionString, new TypeReference<RouteDefinition>() {});
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        publisher.publishEvent(new RefreshRoutesEvent(this.routeDefinitionWriter));
        // 添加缓存
        Map<String, Authority> map = new HashMap<>();
        Authority authority = new Authority();
        authority.setAppKey(appKey);
        authority.setEncryptPublicKey(encryptPublicKey);
        map.put(appKey, authority);
        cacheService.set(CacheKeys.CACHE_AUTH_KEY, map);
    }

    @AfterEach
    void teardown() {
        wireMockServer.stop();
    }

    private void setupStub() {
        wireMockServer.stubFor(get(urlEqualTo("/hi"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(responseBodyString)
                        .withHeader("Content-Type", "application/json")));
    }

    @Test
    void givenMockServer_whenRequest_thenResponseEncrypt() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(TokenProperties.HEADER_KEY_APP_KEY, appKey);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(String.format("http://localhost:%d/hi",port),
                HttpMethod.GET, requestEntity, String.class);
        HttpHeaders header = responseEntity.getHeaders();
        String bodyString = responseEntity.getBody();
        String rsaEncryptKey = header.getFirst(TokenProperties.HEADER_KEY_AES_KEY);
        PrivateKey privateKey = RSAUtils.getPrivateKey(encryPrivateKey);
        String aesPassword = RSAUtils.decryptWithBase64(privateKey, rsaEncryptKey);
        String decryptResponseBodyString = AESUtils.aesDecrypt(bodyString, aesPassword);
        assertThat(decryptResponseBodyString).isEqualTo(responseBodyString);
    }

}
