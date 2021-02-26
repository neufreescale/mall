package org.diwayou.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author gaopeng 2021/2/26
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheLock {

    /**
     * 手动设置key前缀
     */
    String key() default "";

    /**
     * Spring Expression Language (SpEL) 动态计算缓存的key
     *
     * <ul>
     * <li>可以通过{@code #root.method}, {@code #root.target} 引用 {@link java.lang.reflect.Method method}, 目标对象.</li>
     * <li>通过{@code #root.methodName}和{@code #root.targetClass}访问方法名和目标对象Class
     * <li>方法参数可以通过{@code #root.args[1]}, {@code #p1} 或者 {@code #a1}访问.</li>
     * </ul>
     *
     * 在表达式中添加字符用单引号，例如"#a0.id + '_' + #a1.id"，这个表达式表明用第一个参数的id属性和第二个参数的id属性，通过'_'拼接作为key
     * 需要注意如果使用了参数的子属性，需要保证参数不能为null
     */
    String express() default "";

    /**
     * 过期时间
     */
    long ttl() default 60;

    /**
     * 过期时间ttl的单位
     */
    TimeUnit unit() default TimeUnit.SECONDS;
}
