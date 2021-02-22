package com.jdicity.gateway.service;

import com.google.common.base.Throwables;
import com.jdicity.gateway.util.AESUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Write description here.
 *
 * @author zhengweibing3
 * @date 2020/12/27/0027 23:06
 */
@Slf4j
@Service
public class AesEncryptService {

    public String encryptData(String aesKey, String data) {
        if (StringUtils.isEmpty(aesKey)) {
            return null;
        }
        try {
            return AESUtils.aesEncrypt(data, aesKey);
        } catch (Exception e) {
            log.error(Throwables.getStackTraceAsString(e));
        }
        return null;
    }
}
