package com.jdicity.ucuc.exc;

import com.jdicity.ucuc.constant.enums.ResponseCodeEnum;
import lombok.Getter;
import lombok.Setter;


/**
 * 天权相关异常.
 *
 * @author liyingda
 * @date 2020-11-16 10:30
 * @version v1.0.0
 */
public class DrightException extends RuntimeException {
    /**
     * 序列号UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 返回code码
     */
    @Getter
    @Setter
    private int code;

    /**
     * 自定义code+message构造.
     *
     * @param code    返回code码
     * @param message message信息
     */
    public DrightException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * ResponseCodeEnum创建构造.
     *
     * @param codeMsg ResponseCodeEnum对象
     */
    public DrightException(ResponseCodeEnum codeMsg) {
        this(codeMsg.getCode(), codeMsg.getMsg());
    }
}
