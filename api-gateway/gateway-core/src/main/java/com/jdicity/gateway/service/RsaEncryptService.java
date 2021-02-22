package com.jdicity.gateway.service;

import com.google.common.base.Throwables;
import com.jdicity.gateway.util.RSAUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.PublicKey;

/**
 * Write description here.
 *
 * @author zhengweibing3
 * @date 2020/12/28/0028 20:17
 */
@Slf4j
@Service
public class RsaEncryptService {

    public String encryptAesKey(PublicKey publicKey, String aesKey) {
        if (StringUtils.isEmpty(aesKey)) {
            return null;
        }
        try {
            RSAUtils rsaUtils = new RSAUtils();
            return rsaUtils.encryptWithBase64(publicKey, aesKey);
        } catch (Exception e) {
            log.error(Throwables.getStackTraceAsString(e));
        }
        return null;
    }

}
