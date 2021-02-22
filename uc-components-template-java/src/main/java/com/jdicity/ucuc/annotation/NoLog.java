package com.jdicity.ucuc.annotation;

import java.lang.annotation.*;


/**
 * NoLog 定义注解,方法上添加该注解该方法将不会打印请求相应日志.
 *
 * @author vwangliteng
 * @date 2020-11-20 14:24
 * @version V1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface NoLog {
    /**
     * 不打印log注解
     */
    String noLog() default "";
}
