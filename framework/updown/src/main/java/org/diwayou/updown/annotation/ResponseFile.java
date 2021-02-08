package org.diwayou.updown.annotation;

import java.lang.annotation.*;

/**
 * @author gaopeng 2021/2/8
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseFile {

    String name() default "";
}
