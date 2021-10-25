package org.emall.order.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author gaopeng 2021/4/12
 */
@Documented
@Constraint(validatedBy = PhoneValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface Phone {

    String message() default "电话号码不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
