package com.jdicity.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Write description here.
 *
 * @author qixinyuan3
 * @date 2020/12/22 15:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority {
    private Long appId;

    private String appName;

    private String appKey;

    private String pubKey;

    /**
     * aes private key
     */
    private String aesPrivateKey;

    /**
     * 加密公钥
     */
    private String encryptPublicKey;

    /**
     * 加密私钥
     */
    private String encryptPrivateKey;

    /**
     * 签名公钥
     */
    private String signPublicKey;

    /**
     * 签名私钥
     */
    private String signPrivateKey;

    private List<String> apiName;

    private AppReference appReference;
}
