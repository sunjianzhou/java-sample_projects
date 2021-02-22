package com.jdicity.ucuc.exc;

import com.jdicity.ucuc.constant.enums.ResponseCodeEnum;
import com.jdicity.ucuc.schemas.common.UniversalResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 天权相关异常处理.
 *
 * @author liyingda
 * @version v1.0.0
 * @date 2020-11-16 10:30
 */
@Slf4j
@RestControllerAdvice
public class DrightExceptionHandler {
    /**
     * 构造天权异常返回.
     *
     * @param e exception
     * @return response result
     */
    @ExceptionHandler(DrightException.class)
    public UniversalResponse<Object> drightException(DrightException e) {
        log.info("天权调用异常: " + e.getMessage());
        return new UniversalResponse<>(e.getCode(), e.getMessage());
    }

    /**
     * 空指针异常处理.
     *
     * @param e exception
     * @return response result
     */
    @ExceptionHandler(NullPointerException.class)
    public UniversalResponse<Object> nullPointerException(NullPointerException e) {
        log.info("空指针异常: " + e.getMessage());
        return new UniversalResponse<>(ResponseCodeEnum.NULL_POINTER_EXCEPTION);
    }

    /**
     * 业务异常处理.
     *
     * @param e exception
     * @return response result
     */
    @ExceptionHandler(BusinessException.class)
    public UniversalResponse<Object> businessException(BusinessException e) {
        log.info("业务异常: " + e.getMessage());
        return new UniversalResponse<>(e.getCode(), e.getMessage());
    }
}
