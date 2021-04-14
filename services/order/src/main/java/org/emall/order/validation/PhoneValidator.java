package org.emall.order.validation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author gaopeng 2021/4/12
 */
@Slf4j
public class PhoneValidator implements ConstraintValidator<Phone, CharSequence> {

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        log.info("{}", value);
        return false;
    }
}
