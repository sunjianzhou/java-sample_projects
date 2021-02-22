package com.jdicity.gateway.service;

import com.google.common.base.Throwables;
import com.jdicity.gateway.util.AESUtils;
import com.jdicity.gateway.util.RSAUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.PrivateKey;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2020/12/25 16:41
 */
@Slf4j
@Service
public class DecryptService {

    @Value("${gateway.encrypt.privateKey}")
    private String privateKeyString;

    public String decryptData(String aesKey, String data) {

        if (!StringUtils.hasLength(aesKey) || !StringUtils.hasLength(data)) {
            return null;
        }

        try {
            PrivateKey privateKey = RSAUtils.getPrivateKey(privateKeyString);
            String decryptAesKey = RSAUtils.decryptWithBase64(privateKey, aesKey);

            return AESUtils.aesDecrypt(data, decryptAesKey);
        } catch (Exception e) {
            log.error(Throwables.getStackTraceAsString(e));
        }
        return null;
    }
}
