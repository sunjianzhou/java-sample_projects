package com.jdicity.gateway.service;

import com.google.common.base.Throwables;
import com.jdicity.gateway.util.RSAUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 签名校验服务。
 *
 * @author sunjianzhou
 * @date 2020/12/17 21:56
 */
@Slf4j
@Service
public class SignCheckService {

    /**
     * 签名密钥
     */
    @Value("${gateway.sign.privateKey}")
    private String signPrivateKey;

    private static final List<String> APP_FIELD_LIST = Arrays.asList(
            "apiVersion", "appCode", "clientId", "clientVersion", "data", "deviceNo", "lang", "requestId", "ts");

    /**
     * 获取待签名数据。
     * @param headers 请求头
     * @param bodyJsonObject 请求体。
     * @return 待签名数据。
     */
    public String getNeedSignDataFromHeaderAndBody(HttpHeaders headers, Map<String, Object> bodyJsonObject) {

        Object bodyDataObj = bodyJsonObject.get("data");
        if (null == bodyDataObj) {
            bodyDataObj = bodyJsonObject;
        }

        String bodyData = bodyDataObj.toString();
        StringBuilder sb = new StringBuilder();

        APP_FIELD_LIST.forEach(field -> {
            String value = null;
            if ("data".equals(field)) {
                value = bodyData;
            } else {
                value = headers.getFirst(field);
            }
            if (StringUtils.hasLength(value)) {
                sb.append(field).append("=").append(value).append("&");
            }
        });
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 私钥加密，外层base64编码后获得编码结果。
     *
     * @param data 待签名数据
     * @return  签名结果
     */
    public String sign(String data) {
        try {
            Signature signature = Signature.getInstance("Sha256WithRSA");
            PrivateKey privateKey = RSAUtils.getPrivateKey(signPrivateKey);
            log.info("Private key: " + privateKey.toString());
            signature.initSign(privateKey);
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            Encoder encoder = Base64.getEncoder();
            byte[] result = signature.sign();
            return encoder.encodeToString(result);
        } catch (Exception e) {
            log.error(Throwables.getStackTraceAsString(e));
        }
        return "";
    }

    /**
     * 公钥解密，外层base64解码后验证。
     *
     * @param sign 签名结果
     * @param publicKey 公钥
     * @param data 待签名
     * @return True/False
     */
    public boolean verifySign(String sign, String publicKey, String data) {
        boolean verify = false;
        try {
            Signature signature = Signature.getInstance("Sha256WithRSA");
            signature.initVerify(RSAUtils.getPublicKey(publicKey));
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            Decoder decoder = Base64.getDecoder();
            verify = signature.verify(decoder.decode(sign));
        } catch (Exception e) {
            log.error(Throwables.getStackTraceAsString(e));
        }
        return verify;
    }
}
