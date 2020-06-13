package com.chennyh.simpletimetable.system.repository;

import com.chennyh.simpletimetable.system.entity.Course;
import com.chennyh.simpletimetable.system.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Chennyh
 * @date 2020/6/12 15:22
 */
public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByUser(User user, Pageable page);

    Optional<Course> findById(Long id);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void deleteById(Long courseId);
}
