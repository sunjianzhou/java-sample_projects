package com.jdicity.gateway.cache;

/**
 * 缓存接口。
 *
 * @author sunjianzhou
 * @date 2020-12-10 14:48
 */
public interface CacheService {
    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return Boolean
     */
    Boolean expire(String key, Long time);


    /**
     * 根据key获取过期时间
     *
     * @param key 键 不能为 null
     * @return 时间(秒) 返回 0代表为永久有效
     */
    Long getExpire(String key);

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    Boolean hasKey(String key);

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    void del(String... key);


    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    Object get(String key);
    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    Boolean set(String key, Object value);

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    Boolean set(String key, Object value, Long time);
}
