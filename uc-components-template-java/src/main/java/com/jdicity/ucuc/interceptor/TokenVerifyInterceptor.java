package com.jdicity.ucuc.interceptor;

import com.jdicity.ucuc.constant.enums.ResponseCodeEnum;
import com.jdicity.ucuc.constant.Common;
import com.jdicity.ucuc.constant.Dright;
import com.jdicity.ucuc.exc.DrightException;
import com.jdicity.ucuc.interceptor.threadlocals.InspectionContext;
import com.jdicity.ucuc.interceptor.threadlocals.UserInfo;

import com.alibaba.fastjson.JSON;
import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 验证token拦截器.
 *
 * @author liyingda
 * @date 2020-11-19 18:15
 * @version v1.0.0
 */
@Component
@Slf4j
public class TokenVerifyInterceptor extends HandlerInterceptorAdapter {

    /**
     * 天权baseUrl
     */
    @Value("${dright.baseUrl}")
    private String baseUrl;

    /**
     * 天权获取token的url
     */
    @Value("${dright.tokenVerify}")
    private String tokenVerifyUrl;

    /**
     * restTemplate网络请求
     */
    @Resource
    private RestTemplate restTemplate;

    /**
     * 验证token实现.
     *
     * @param request request
     * @param response response
     * @param handler handler
     * @return true or false
     * @throws Exception 验证过程可能抛出异常.
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        log.debug("上下文拦截器preHandle方法开始执行.");
        String authorizationValue = ServletUtil.getHeaderIgnoreCase(request, "Authorization");
        log.info("用户输入token为 {}.", authorizationValue);

        if (null != authorizationValue) {
            Map<String, Object> map = inspectionToken(authorizationValue);
            Integer code = (Integer) map.get(Dright.RESPONSE_KEY_CODE);
            if (code != 0) {
                log.warn("Token验证失败，返回值{}，报错{}.",
                        map.get(Dright.RESPONSE_KEY_CODE), map.get(Dright.RESPONSE_KEY_MSG));
                String respJsonStr = (JSON.toJSONString(map));
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                PrintWriter writer = response.getWriter();
                writer.write(respJsonStr);
                writer.flush();
                return false;
            }

            creatContext(map);
        } else {
            log.info("用户未输入token.");
            throw new DrightException(ResponseCodeEnum.TOKEN_NOT_PROVIDED);
        }

        log.debug("上下文拦截器preHandle方法执行结束.");
        return true;
    }

    /**
     * 创建上下文数据.
     *
     * @param map param
     */
    private void creatContext(Map<String, Object> map) {
        Map<String, Object> data = (Map<String, Object>) map.get(Dright.RESPONSE_KEY_DATA);
        Map<String, Object> clientInfo = (Map<String, Object>) data.get(Dright.RESPONSE_KEY_CLIENT_INFO);
        UserInfo txUserInfo;
        if (null != clientInfo) {
            txUserInfo = JSON.parseObject(JSON.toJSONString(clientInfo), UserInfo.class);
            txUserInfo.setType(1);
        } else {
            Map<String, Object> userInfo = (Map<String, Object>) data.get(Dright.RESPONSE_KEY_USER_INFO);
            txUserInfo = JSON.parseObject(JSON.toJSONString(userInfo), UserInfo.class);
            txUserInfo.setType(0);
        }

        InspectionContext.setContext(txUserInfo);
        log.debug("已设置上下文");
    }

    /**
     * token验证请求.
     *
     * @param authorizationValue 待验token
     * @return token验证结果
     */
    private Map<String, Object> inspectionToken(String authorizationValue) {

        String url = baseUrl + tokenVerifyUrl;
        log.info("Token验证地址：{}.", url);

        Map<String, String> map = new HashMap<>(Common.DEFAULT_CAPACITY);
        map.put("Authorization", authorizationValue);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(map);
        LinkedMultiValueMap<String, Object> valueMap = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(valueMap, httpHeaders);

        ResponseEntity<Map> mapResponseEntity = restTemplate.postForEntity(url, entity, Map.class);

        return mapResponseEntity.getBody();
    }

    /**
     * 调用完后释放线程资源
     * @param request request
     * @param response response
     * @param handler handler
     * @param ex ex
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) {
        InspectionContext.remove();
    }

}
