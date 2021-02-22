package com.jdicity.gateway.service;

import com.jdicity.gateway.constant.HeaderEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2020/12/29 14:53
 */
@Service
public class ParamCheckService {
    public String checkParamFromHeader(HttpHeaders headers) {
        return Arrays.stream(HeaderEnum.values())
                .filter(param -> param.isRequire() && StringUtils.isEmpty(headers.getFirst(param.getValue())))
                .findAny()
                .map(HeaderEnum::getValue)
                .orElse(null);
    }
}
