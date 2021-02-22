package com.jdicity.ucuc.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Bean拷贝.
 *
 * @author liyingda
 * @date 2020-11-18 11:28
 * @version V1.0.0
 **/
@Slf4j
public class BeanUtil {

    /**
     * 集合对象拷贝.
     *
     * @param sourceList       数据源集合
     * @param targetClass      目标对象
     * @param ignoreProperties 其他配置
     * @param <T>              数据源类型
     * @param <V>              复制后的类型
     * @return 目标对象集合
     */
    public static <T, V> List<V> map2List(List<T> sourceList, Class<V> targetClass, String... ignoreProperties) {
        List<V> targetList = new ArrayList<>();
        if (sourceList != null && sourceList.size() != 0) {
            for (T node : sourceList) {
                targetList.add(map2obj(node, targetClass, ignoreProperties));
            }
        }
        return targetList;
    }

    /**
     * 拷贝对象.
     *
     * @param source 数据源对象
     * @param targetClass 目标对象
     * @param ignoreProperties 其他配置信息
     * @param <T> 目标对象类型
     * @return 目标对象
     */
    public static <T> T map2obj(Object source, Class<T> targetClass, String... ignoreProperties) {
        if (source == null) {
            return null;
        }
        try {
            T target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target, ignoreProperties);
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("类转换错误:{}", e.getMessage(), e);
            return null;
        }
    }
}
