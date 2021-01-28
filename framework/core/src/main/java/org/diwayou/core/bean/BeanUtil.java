package org.diwayou.core.bean;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * @author gaopeng 2021/1/29
 */
public class BeanUtil {

    /**
     * 不复制null字段
     */
    public static void copyProperties(Object source, Object target) throws BeansException {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    public static <T> T to(Object source, Class<T> targetClass) {
        T t = BeanUtils.instantiateClass(targetClass);
        BeanUtils.copyProperties(source, t);

        return t;
    }

    private static String[] getNullPropertyNames(Object source) {
        return getNullPropertyNameSet(source).toArray(new String[0]);
    }

    private static Set<String> getNullPropertyNameSet(Object source) {
        Assert.notNull(source, "source object must not be null");

        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            Object propertyValue = beanWrapper.getPropertyValue(propertyName);

            if (propertyValue == null) {
                emptyNames.add(propertyName);
            }
        }

        return emptyNames;
    }
}
