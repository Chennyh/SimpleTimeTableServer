package com.chennyh.simpletimetable.system.validator;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({PARAMETER, FIELD})
@Retention(RUNTIME)
public @interface Email {
    String message() default "邮箱格式错误";

    Class[] groups() default {};

    Class[] payload() default {};
}
