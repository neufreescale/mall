package org.diwayou.pf.annotation;

import org.diwayou.pf.PluginRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gaopeng 2021/1/15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({PluginRegistrar.class})
public @interface EnablePlugin {

    String pluginsRoot() default "";

    String systemVersion() default "";
}
