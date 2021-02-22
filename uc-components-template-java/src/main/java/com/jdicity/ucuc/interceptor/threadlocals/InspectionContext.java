package com.jdicity.ucuc.interceptor.threadlocals;


/**
 * 线程上下文.
 *
 * @author liyingda
 * @date 2020-11-19 18:12
 * @version v1.0.0
 */
public class InspectionContext {
    /**
     * 线程安全threadLocal
     */
    private static final ThreadLocal<UserInfo> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 空构造函数
     */
    public InspectionContext() {
    }

    /**
     * 获取ThreadLocal.
     *
     * @return 返回ThreadLocal
     */
    public static UserInfo get() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 设置ThreadLocal.
     *
     * @param info 用户数据
     */
    public static void setContext(UserInfo info) {
        CONTEXT_HOLDER.set(info);
    }

    /**
     * 释放线程资源.
     */
    public static void remove() {
        CONTEXT_HOLDER.remove();
    }
}
