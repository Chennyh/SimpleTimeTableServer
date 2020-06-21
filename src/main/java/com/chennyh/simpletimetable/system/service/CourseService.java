package com.chennyh.simpletimetable.system.service;

import com.chennyh.simpletimetable.system.entity.Course;
import com.chennyh.simpletimetable.system.entity.User;
import com.chennyh.simpletimetable.system.exception.CourseIdNotFoundException;
import com.chennyh.simpletimetable.system.exception.UserIdNotFoundException;
import com.chennyh.simpletimetable.system.repository.CourseRepository;
import com.chennyh.simpletimetable.system.repository.UserRepository;
import com.chennyh.simpletimetable.system.web.representation.CourseRepresentation;
import com.chennyh.simpletimetable.system.web.request.CourseAddRequest;
import com.chennyh.simpletimetable.system.web.request.CourseUpdateRequest;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

    public Page<CourseRepresentation> getAll(int pageNum, int pageSize) {
        return courseRepository.findAll(PageRequest.of(pageNum, pageSize)).map(Course::toCourseRepresentation);
    }

    public ArrayList<CourseRepresentation> getCourse(Long userId, boolean today) {
        ArrayList<CourseRepresentation> courseRepresentations = new ArrayList<>();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserIdNotFoundException(ImmutableMap.of("userId", userId)));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int currentWeek = Calendar.DAY_OF_WEEK;

        ArrayList<Course> courses = courseRepository.findByUser(user);
        for (Course course : courses) {
            if (today) {
                if (course.getDay() == currentWeek) {
                    courseRepresentations.add(course.toCourseRepresentation());
                }
            } else {
                courseRepresentations.add(course.toCourseRepresentation());
            }
        }
        return courseRepresentations;
    }

    public void delete(Long courseId) {
        try {
            courseRepository.deleteById(courseId);
        } catch (EmptyResultDataAccessException e) {
            throw new CourseIdNotFoundException(ImmutableMap.of("courseId", courseId));
        }
    }

    public void update(Long courseId, CourseUpdateRequest courseUpdateRequest) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new CourseIdNotFoundException(ImmutableMap.of("courseId", courseId)));
        course.updateForm(courseUpdateRequest);
        courseRepository.save(course);
    }
}
