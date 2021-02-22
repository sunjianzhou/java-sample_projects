package com.jdicity.gateway.filters.constant;

/**
 * 统一管理过滤器顺序。
 *
 * @author sunjianzhou
 * @date 2020/12/20 13:53
 */

public enum FilterOrderEnum {
    // Order for pre, lowest first.
    TOKEN_CHECK("token_check", 1, "Token check filter."),
    HEADER_CHECK("head_check", 2, "Header check filter with params pre-set."),
    ACCESS_TOKEN_CHECK("access_token_check", 3, "Access token check for app client."),
    REQUEST_BODY_DECRYPT("request_body_decrypt", 4, "Decrypt the request body."),
    API_PERMISSION("api_permission", 10, "Permission check for api."),
    APP_SIGN_CHECK("app_sign_check", 11, "Request sign check for app client."),
    BODY_PARSE_CHECK("request_body_parse", 20, "Parse the request body."),
    CONTENT_TYPE_CHANGE("content_type_change", 30, "Change the content type of request."),
    APP_REQUEST_BODY_GET_DATA("request_body_get_data", 31, "Get data from request body."),

    // Order for post, lowest last.
    AES_RESPONSE_ENCRYPT("aes_response_encrypt", -2, "Add AES encrypt for response"),
    APP_SIGN_ADD("app_sign_add", -3, "Add sign into response header for app client.");

    FilterOrderEnum(String filter, int order, String desc) {
        this.desc = desc;
        this.filter = filter;
        this.order = order;
    }

    private final String filter;
    private final int order;
    private final String desc;

    public int getOrder() {
        return order;
    }

    public String getDesc() {
        return desc;
    }

    public String getFilter() {
        return filter;
    }
}
