package com.jdicity.ucuc.aop;

import com.alibaba.fastjson.JSON;
import com.jdicity.ucuc.annotation.NoLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


/**
 * LogAspect 请求日志切面.
 *
 * @author vwangliteng
 * @date 2020-11-20 14:24
 * @version V1.0.0
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * 线程内安全的存储对象
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 定义切入点
     */
    @Pointcut("execution( * com.jdicity.ucuc.controller..*.*(..))")
    public void requestLog() {
    }

    /**
     * 打印请求信息.
     *
     * @param joinPoint joinPoint
     */
    @Before("requestLog()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());

        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();

        if (requestAttributes instanceof ServletRequestAttributes) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest request = servletRequestAttributes.getRequest();

            // 打印当前的请求路径
            log.info("RequestMapping:[{}]", request.getRequestURI());
        }

        // 获取方法
        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Method targetMethod = methodSignature.getMethod();
            // 获取注解并生成key
            NoLog noLog = targetMethod.getAnnotation(NoLog.class);
            if (noLog == null) {
                // 打印请求参数
                log.info("RequestParam:{}", JSON.toJSONString(joinPoint.getArgs()));
            }
        }
    }

    /**
     * 打印返回值以及方法消耗时间.
     *
     * @param response 返回值
     */
    @AfterReturning(returning = "response", pointcut = "requestLog()")
    public void doAfterRunning(Object response) {
        // 打印返回值信息
        log.info("Response:{}", JSON.toJSONString(response));
        // 打印请求耗时
        log.info("Request spend times : [{}ms]", System.currentTimeMillis() - startTime.get());
        remove();
    }

    /**
     * 释放线程资源
     */
    public void remove() {
        if (null != startTime) {
            startTime.remove();
        }
    }
}
