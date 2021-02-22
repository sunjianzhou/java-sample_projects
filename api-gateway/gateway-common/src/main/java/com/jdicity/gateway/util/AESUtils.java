package com.jdicity.gateway.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * AES加解密模块.
 *
 * @author sunjianzhou
 * @date 2020/12/14 14:10
 */
@Slf4j
public class AESUtils {

    private static final String KEY_ALGORITHMS = "AES";

    // 加密
    public static String aesEncrypt(String sSrc, String privateKey) {
        if (!StringUtils.hasLength(sSrc)) {
            return null;
        }
        byte[] encrypted = new byte[0];
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            byte[] raw = privateKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, KEY_ALGORITHMS);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            encrypted = cipher.doFinal(sSrc.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
            log.error(e.toString());
        }
        return Base64.encodeBase64String(encrypted);
    }

    // 解密
    public static String aesDecrypt(String sSrc, String privateKey) {
        if (!StringUtils.hasLength(sSrc)) {
            return null;
        }
        try {
            byte[] raw = privateKey.getBytes(StandardCharsets.US_ASCII);
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, KEY_ALGORITHMS);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] encrypted1 = Base64.decodeBase64(sSrc.getBytes(StandardCharsets.UTF_8));
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
    }
}
