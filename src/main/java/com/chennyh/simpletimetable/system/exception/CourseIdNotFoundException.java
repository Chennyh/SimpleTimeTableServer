package com.chennyh.simpletimetable.system.exception;

import java.util.Map;

/**
 * @author Chennyh
 * @date 2020/6/13 10:01
 */
public class CourseIdNotFoundException extends BaseException{

    public CourseIdNotFoundException(Map<String, Object> data) {
        super(ErrorCode.COURSE_ID_NOT_FOUND, data);
    }
}
