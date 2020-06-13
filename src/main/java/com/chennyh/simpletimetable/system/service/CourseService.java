package com.chennyh.simpletimetable.system.service;

import com.chennyh.simpletimetable.system.entity.Course;
import com.chennyh.simpletimetable.system.entity.User;
import com.chennyh.simpletimetable.system.exception.UserIdNotFoundException;
import com.chennyh.simpletimetable.system.repository.CourseRepository;
import com.chennyh.simpletimetable.system.repository.UserRepository;
import com.chennyh.simpletimetable.system.web.request.CourseAddRequest;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Chennyh
 * @date 2020/6/12 15:31
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public void save(CourseAddRequest courseAddRequest) {
        Course course = Course.of(courseAddRequest);
        User user = userRepository.findById(courseAddRequest.getUserId()).orElseThrow(() -> new UserIdNotFoundException(ImmutableMap.of("userId", courseAddRequest.getUserId())));
        course.setUser(user);
        courseRepository.save(course);
    }
}
