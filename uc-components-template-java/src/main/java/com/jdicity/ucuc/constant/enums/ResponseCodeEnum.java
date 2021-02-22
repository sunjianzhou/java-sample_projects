package com.jdicity.ucuc.constant.enums;

import lombok.Getter;


/**
 * 业务状态返回码枚举.
 *
 * @author liyingda
 * @date 2020-11-13 16:30
 * @version v1.0.0
 */
public enum ResponseCodeEnum {
    /**
     * success code
     */
    SUCCESS(0, "success"),

    NULL_POINTER_EXCEPTION(10002, "空指针异常"),
    ENTRY_NOT_EXIST(10402, "不存在"),
    BATCH_UPDATE_FAILED(10403, "无效的修改操作"),
    BATCH_DELETED_FAILED(10404, "无效的删除操作"),
    BATCH_ADD_FAILED(10405, "无效的新增操作"),
    TOKEN_GET_FAILED(10503, "获取token失败"),
    TOKEN_NOT_PROVIDED(10509, "用户未输入token");

    /**
     * code
     */
    @Getter
    private final int code;

    /**
     * message
     */
    @Getter
    private final String msg;

    /**
     * @param code code
     * @param msg  msg
     */
    ResponseCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
