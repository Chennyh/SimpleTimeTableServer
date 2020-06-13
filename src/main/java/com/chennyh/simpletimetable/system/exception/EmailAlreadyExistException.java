package com.chennyh.simpletimetable.system.exception;

import java.util.Map;

/**
 * @author shuang.kou
 */
public class EmailAlreadyExistException extends BaseException {

    public EmailAlreadyExistException(Map<String, Object> data) {
        super(ErrorCode.EMAIL_ALREADY_EXIST, data);
    }
}
