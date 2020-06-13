package com.chennyh.simpletimetable.system.repository;

import com.chennyh.simpletimetable.system.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Chennyh
 * @date 2020/6/12 15:22
 */
public interface CourseRepository extends JpaRepository<Course, Long> {
}
