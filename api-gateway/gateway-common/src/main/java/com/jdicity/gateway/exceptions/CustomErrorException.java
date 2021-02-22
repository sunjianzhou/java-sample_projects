package com.jdicity.gateway.exceptions;

import com.jdicity.gateway.constant.ResponseCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Write description here.
 *
 * @author zhengweibing3
 * @date 2020/12/16/0016 20:57
 */
@Data
@AllArgsConstructor
public class CustomErrorException extends RuntimeException {

    /**
     * 代码
     */
    private String code;

    /**
     * 信息
     */
    private String message;

    public CustomErrorException(ResponseCodeEnum code) {
        super(code.getMessage());
        this.message = code.getMessage();
        this.code = code.getCode();
    }
}
