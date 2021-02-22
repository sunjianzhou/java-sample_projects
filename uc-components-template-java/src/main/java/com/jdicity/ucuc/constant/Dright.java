package com.jdicity.ucuc.constant;


/**
 * Dright constants.
 *
 * @author liyingda
 * @date 2020-11-19 17:34
 */
public class Dright {
    /**
     * 天权客户端请求参数：授权方式.
     */
    public static final String REQUEST_KEY_CLIENT_GRANT = "grant_type";

    /**
     * 天权客户端请求参数：客户端id.
     */
    public static final String REQUEST_KEY_CLIENT_ID = "client_id";

    /**
     * 天权客户端请求参数：客户端secret.
     */
    public static final String REQUEST_KEY_CLIENT_SECRET = "client_secret";

    /**
     * 天权获取token返回结果参数：access_token.
     */
    public static final String RESPONSE_KEY_ACCESS_TOKEN = "access_token";

    /**
     * 天权客户端token验证返回客户端信息.
     */
    public static final String RESPONSE_KEY_CLIENT_INFO = "client_info";

    /**
     * 天权获取token返回结果参数：业务状态码.
     */
    public static final String RESPONSE_KEY_CODE = "code";

    /**
     * 天权获取token返回结果参数：data.
     */
    public static final String RESPONSE_KEY_DATA = "data";

    /**
     * 天权获取token返回结果参数：message.
     */
    public static final String RESPONSE_KEY_MSG = "message";

    /**
     * 天权获取token返回结果参数：HTTP 状态码.
     */
    public static final Integer SUCCESS_CODE = 200;

    /**
     * 天权客户端token验证返回租户.
     */
    public static final String RESPONSE_KEY_TENANT = "tenantId";

    /**
     * 天权用户token验证返回用户信息.
     */
    public static final String RESPONSE_KEY_USER_INFO = "user_info";
}
