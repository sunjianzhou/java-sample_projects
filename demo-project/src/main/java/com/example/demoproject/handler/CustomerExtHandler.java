package com.example.demoproject.handler;

import com.example.demoproject.utils.JsonData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2021/1/27 10:31
 */
@RestControllerAdvice
public class CustomerExtHandler {

    @ExceptionHandler(value = Exception.class)
    JsonData handlerException(Exception e, HttpServletRequest request){
        return JsonData.buildError(-2, "服务端异常");
    }
}
