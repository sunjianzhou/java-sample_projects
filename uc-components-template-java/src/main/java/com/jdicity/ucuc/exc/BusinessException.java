package com.jdicity.ucuc.exc;

import com.jdicity.ucuc.constant.enums.ResponseCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常类.
 *
 * @author vwangliteng
 * @date 2020-11-24 10:30
 * @version v1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {
    /**
     * 序列号UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 返回code码
     */
    private int code;

    /**
     * 自定义code+message构造.
     *
     * @param code    返回code码
     * @param message message信息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * ResponseCodeEnum创建构造.
     *
     * @param codeMsg ResponseCodeEnum对象
     */
    public BusinessException(ResponseCodeEnum codeMsg) {
        this(codeMsg.getCode(), codeMsg.getMsg());
    }
}
