package com.chennyh.simpletimetable.system.web.controller;

import com.chennyh.simpletimetable.system.service.CourseService;
import com.chennyh.simpletimetable.system.web.request.CourseAddRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @ApiOperation(value = "课程添加接口", notes = "向数据库中添加一门课程")
    public ResponseEntity addCourse(@RequestBody @Valid CourseAddRequest courseAddRequest) {
        courseService.save(courseAddRequest);
        return ResponseEntity.ok().build();
    }
}
