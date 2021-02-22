package com.jdicity.gateway.constant;

/**
 * Header相关常量。
 *
 * @author sunjianzhou
 * @date 2020/12/18 16:02
 */
public enum HeaderEnum {

    /**
     * APP端令牌
     */
    ACCESS_TOKEN("accessToken", false),

    /**
     * AES 加密的key。
     */
    AES_KEY("aesKey", false),

    /**
     * 客户端秘钥
     */
    APP_KEY("appKey", true),

    /**
     * APP code
     */
    APP_CODE("appCode", false),

    /**
     * APP TS
     */
    TS("ts", false),

    /**
     * APP端客户端Id
     */
    CLIENT_ID("clientId", true),

    /**
     * APP 请求ID
     */
    REQUEST_ID("requestId", false),

    /**
     * APP SECRET
     */
    SECRET("secret", false),

    /**
     * APP端签名
     */
    SIGN("sign", true);

    String value;
    Boolean require;

    HeaderEnum(String value, Boolean require) {
        this.value = value;
        this.require = require;
    }

    public String getValue() {
        return value;
    }

    public boolean isRequire() {
        return require;
    }
}
