package com.jdicity.gateway.constant;

/**
 * 统一管理响应码。
 *
 * @author sunjianzhou
 * @date 2020/12/18 16:51
 */
public enum ResponseCodeEnum {

    APP_CODE_ERROR("40001", "appCode不能为空。"),
    CLIENT_ID_ERROR("40002", "clientId不能为空"),
    NONCE_ERROR("40003", "随机数重复错误"),
    SECRET_MISSING("40004", "secret缺失。"),
    SIGN_ERROR("40005", "签名错误。"),
    REQUIRED_PARAMS_MISSING_ERROR("40006", "必要参数缺失错误。"),
    REQUEST_TIMESTAMP_TIMEOUT("40007", "请求时间戳超时错误。"),
    ACCESS_TOKEN_ERROR("40008", "accessToken错误。");

    private String code;
    private String message;

    ResponseCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取响应码。
     * @return this.code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 获取响应信息。
     * @return this.message
     */
    public String getMessage() {
        return this.message;
    }
}
