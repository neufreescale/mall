package org.diwayou.updown.annotation;

import java.lang.annotation.*;

/**
 * @author gaopeng 2021/2/8
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestFile {

    String name() default "";

    Class<?> dataClass();

    boolean required() default true;
}
