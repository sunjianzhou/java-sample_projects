package com.jdicity.gateway.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;

/**
 * RSA加密.
 *
 * @author sunjianzhou
 * @date 2020/12/14 14:04
 */
@Slf4j
@NoArgsConstructor
public class RSAUtils {

    /**
     * 私钥
     */
    private RSAPrivateKey privateKey;

    /**
     * 公钥
     */
    private RSAPublicKey publicKey;

    /**
     * 获取私钥
     *
     * @return 当前的私钥对象
     */
    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     * 获取公钥
     *
     * @return 当前的公钥对象
     */
    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public RSAUtils(String publicKey, String privateKey) {

        try {
            loadPublicKey(publicKey);
            loadPrivateKey(privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public void loadPublicKey(String publicKeyStr) throws Exception {

        try {
            Decoder decoder = Base64.getDecoder();
            byte[] buffer = decoder.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            this.publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            log.info("当前公钥为：" + this.publicKey);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }

    }

    public void loadPrivateKey(String privateKeyStr) throws Exception {

        try {
            Decoder decoder = Base64.getDecoder();
            byte[] buffer = decoder.decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
            log.info("当前私钥为：" + this.publicKey);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }

    }

    public static String decryptWithBase64(PrivateKey privateKey, String base64String) throws Exception {
        byte[] binaryData = decrypt(privateKey, Base64.getDecoder().decode(base64String));
        return new String(binaryData, StandardCharsets.UTF_8);
    }

    public String encryptWithBase64(PublicKey publicKey, String string) throws Exception {
        byte[] binaryData = encrypt(publicKey, string.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(binaryData);
    }

    /**
     * 解密过程
     *
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static byte[] decrypt(PrivateKey privateKey, byte[] cipherData) throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            log.info("provider: {}", cipher.getProvider().getClass().getName());
            return cipher.doFinal(cipherData);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    /**
     * 加密过程。
     *
     * @param publicKey     公钥。
     * @param plainTextData 请求文本数据。
     * @return 加密后的字节码。
     * @throws Exception 抛出异常。
     */
    public byte[] encrypt(PublicKey publicKey, byte[] plainTextData) throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            log.info("provider: {}", cipher.getProvider().getClass().getName());
            return cipher.doFinal(plainTextData);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 根据字符串生成公钥。
     * @param key key
     * @return  PublicKey
     * @throws Exception 编码异常
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        Decoder decoder = Base64.getDecoder();
        byte[] keyBytes = decoder.decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 根据字符串得到私钥
     * @param key key
     * @return PrivateKey
     * @throws Exception 编码解码异常
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        try {
            Decoder decoder = Base64.getDecoder();
            byte[] buffer = decoder.decode(key);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法。");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法。");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空。");
        }
    }
}
