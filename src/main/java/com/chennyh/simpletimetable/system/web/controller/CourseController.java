package com.chennyh.simpletimetable.system.web.controller;

import com.chennyh.simpletimetable.security.utils.CurrentUserUtils;
import com.chennyh.simpletimetable.system.service.CourseService;
import com.chennyh.simpletimetable.system.web.representation.CourseRepresentation;
import com.chennyh.simpletimetable.system.web.request.CourseAddRequest;
import com.chennyh.simpletimetable.system.web.request.CourseUpdateRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * @author Chennyh
 * @date 2020/6/12 15:19
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/courses")
@Api(tags = "课程相关接口")
public class CourseController {

    private final CourseService courseService;
    private final CurrentUserUtils currentUserUtils;

    @PostMapping()
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @ApiOperation(value = "课程添加接口", notes = "向数据库中添加一门课程")
    public ResponseEntity addCourse(@RequestBody @Valid CourseAddRequest courseAddRequest) {
        System.out.println("当前访问添加课程接口的用户为：" + currentUserUtils.getCurrentUser().getUsername());
        courseService.save(courseAddRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @ApiOperation(value = "查询课程列表", notes = "查询数据库中的所有课程信息")
    public ResponseEntity<Page<CourseRepresentation>> getAllCourse(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        System.out.println("当前访问查询课程接口的用户为：" + currentUserUtils.getCurrentUser().getUsername());
        Page<CourseRepresentation> allCourse = courseService.getAll(pageNum, pageSize);
        return ResponseEntity.ok().body(allCourse);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @ApiOperation(value = "查询用户课程", notes = "通过用户ID查询数据库中的课程信息")
    public ResponseEntity<ArrayList<CourseRepresentation>> getUserCourse(@PathVariable Long userId, @RequestParam(value = "today", defaultValue = "false") boolean today) {
        System.out.println("当前访问查询课程接口的用户为：" + currentUserUtils.getCurrentUser().getUsername());
        ArrayList<CourseRepresentation> allUser = courseService.getUser(userId, today);
        return ResponseEntity.ok().body(allUser);
    }

    @DeleteMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @ApiOperation(value = "删除一个课程", notes = "通过课程ID删除一个课程信息")
    public ResponseEntity deleteCourseByCourseId(@PathVariable Long courseId) {
        System.out.println("当前访问删除课程接口的用户为：" + currentUserUtils.getCurrentUser().getUsername());
        courseService.delete(courseId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @ApiOperation(value = "修改一个课程", notes = "通过课程ID修改一个课程信息")
    public ResponseEntity updateById(@PathVariable Long courseId, @RequestBody CourseUpdateRequest courseUpdateRequest) {
        System.out.println("当前访问修改课程接口的用户为：" + currentUserUtils.getCurrentUser().getUsername());
        courseService.update(courseId, courseUpdateRequest);
        return ResponseEntity.ok().build();
    }
}
