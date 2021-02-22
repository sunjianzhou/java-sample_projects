package com.jdicity.gateway.constant;

/**
 * 构建JWT Token相关常量。
 * @author sunjianzhou
 * @date 2020-12-10 14:48
 */
public class TokenProperties {
    /**
     * Token过期时间。
     */
    public static final long EXPIRE = 24 * 60 * 60 * 1000;

    /**
     * 加密秘钥
     */
    public static final String SECRET = "uc.secret";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "ucuc";

    /**
     * 主题
     */
    public static final String SUBJECT = "ucSubject";

    /**
     * payload，生成token的必要元素。
     */
    public static final String APP_KEY = "appKey";

    /**
     * app_secret
     */
    public static final String APP_SECRET = "appSecret";

    /**
     * app_key，储存头部的key
     */
    public static final String HEADER_KEY_APP_KEY = "app_key";

    public static final String HEADER_KEY_AES_KEY = "aesKey";

}
