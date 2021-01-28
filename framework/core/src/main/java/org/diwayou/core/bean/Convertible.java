package org.diwayou.core.bean;

import org.springframework.beans.BeanUtils;

/**
 * @author gaopeng 2021/1/29
 */
public interface Convertible {

    default <T> T to(Class<T> targetClass) {
        T t = BeanUtils.instantiateClass(targetClass);

        BeanUtil.copyProperties(this, t);

        return t;
    }

    /**
     * 不复制null字段
     */
    default <T> T copyTo(T t) {
        BeanUtil.copyProperties(this, t);

        return t;
    }

    default <T> T copyAll(T t) {
        BeanUtils.copyProperties(this, t);

        return t;
    }
}
