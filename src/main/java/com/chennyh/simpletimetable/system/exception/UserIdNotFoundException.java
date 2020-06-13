package com.chennyh.simpletimetable.system.exception;

import java.util.Map;

/**
 * @author Chennyh
 * @date 2020/6/13 10:01
 */
public class UserIdNotFoundException extends BaseException{

    public UserIdNotFoundException(Map<String, Object> data) {
        super(ErrorCode.USER_ID_NOT_FOUND, data);
    }
}
