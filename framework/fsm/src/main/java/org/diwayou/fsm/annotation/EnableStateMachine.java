package org.diwayou.fsm.annotation;

import org.diwayou.fsm.StateMachineRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gaopeng 2021/1/19
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({StateMachineRegistrar.class})
public @interface EnableStateMachine {
}
