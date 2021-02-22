package com.jdicity.gateway.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

/**
 * 对请求的基础信息解析。
 *
 * @author sunjianzhou
 * @date 2020/12/13 20:14
 */
@Slf4j
public class RequestUtils {

    public static HashMap<String, Object> bodyParse(MediaType contentType, String body) throws Exception {
        log.info("BodyParser：当前contentType为：" + contentType);
        ObjectMapper bodyJsonObject = new ObjectMapper();
        HashMap<String, Object> bodyMap = new HashMap<>();
        if (MediaType.APPLICATION_FORM_URLENCODED.equals(contentType)) {
            String[] bodyArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(body, "&");
            if (bodyArray != null) {
                for (String entry : bodyArray) {
                    int i = entry.indexOf("=");

                    String bodyValue = entry.substring(i + 1);
                    try {
                        bodyValue = URLDecoder.decode(bodyValue, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        log.warn("无法序列化body,decode异常", e);
                    }
                    bodyMap.put(entry.substring(0, i), bodyValue);
                }
            }
        } else if (MediaType.APPLICATION_JSON.equals(contentType) || MediaType.APPLICATION_JSON_UTF8.equals(contentType)
                || MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(contentType.toString())) {
            bodyMap = bodyJsonObject.readValue(body, HashMap.class);
        } else {
            log.error("无法序列化body，当前contentType为：" + contentType.toString() + " body: " + body);
            throw new Exception("序列化失败。");
        }
        return bodyMap;
    }
}
