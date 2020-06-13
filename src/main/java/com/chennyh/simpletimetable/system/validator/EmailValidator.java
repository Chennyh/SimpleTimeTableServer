package com.chennyh.simpletimetable.system.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {
    /**
     * 邮箱格式验证
     */
    private static final String EMAIL_REG_EXP = "^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            // can be null
            return true;
        }
        return email.matches(EMAIL_REG_EXP);
    }
}
