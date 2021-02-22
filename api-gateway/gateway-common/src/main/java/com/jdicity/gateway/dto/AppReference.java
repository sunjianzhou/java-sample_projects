package com.jdicity.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2020/12/29 14:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppReference {
    /**
     * 主键
     */
    private Long id;

    /**
     * 客户端编号
     */
    private String clientId;

    /**
     * app编码
     */
    private String appCode;

    /**
     * 客户端类型pc/android/ios/applet/h5
     */
    private String clientType;

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

    /**
     * secret密钥
     */
    private String secret;

    /**
     * 检查access_token
     */
    private String accessTokenCheck;

    /**
     * 请求参数是否加密
     */
    private String requestEncrypt;

    /**
     * 响应结果是否加密
     */
    private String responseEncrypt;

    /**
     * 请求参数是否签名
     */
    private String requestSign;

    /**
     * 签名算法类型
     */
    private String requestSignType;

    /**
     * 响应结果是否签名
     */
    private String responseSign;

    /**
     * 检查参数完整性
     */
    private String paramCompleteCheck;

    /**
     * 检查时间戳
     */
    private String paramTsCheck;

    /**
     * 检查随机字符串
     */
    private String paramNonceCheck;

    /**
     * 状态[可用normal,不可用deleted]
     */
    private String status;

    /**
     * 记录创建者
     */
    private String creator;
}
