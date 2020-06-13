package com.chennyh.simpletimetable.system.web.controller;

import com.chennyh.simpletimetable.system.web.representation.UserRepresentation;
import com.chennyh.simpletimetable.security.utils.CurrentUserUtils;
import com.chennyh.simpletimetable.system.service.UserService;
import com.chennyh.simpletimetable.system.web.request.UserRegisterRequest;
import com.chennyh.simpletimetable.system.web.request.UserUpdateRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author shuang.kou
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/users")
@Api(tags = "用户相关接口")
public class UserController {

    private final UserService userService;
    private final CurrentUserUtils currentUserUtils;

    @PostMapping("/sign-up")
    @ApiOperation(value = "用户注册接口", notes = "用户使用此接口进行注册操作")
    public ResponseEntity signUp(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        userService.save(userRegisterRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @ApiOperation(value = "获取所有用户列表", notes = "需要登录，否则会获取失败")
    public ResponseEntity<Page<UserRepresentation>> getAllUser(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        System.out.println("当前访问该接口的用户为：" + currentUserUtils.getCurrentUser().getUsername());
        Page<UserRepresentation> allUser = userService.getAll(pageNum, pageSize);
        return ResponseEntity.ok().body(allUser);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ApiOperation(value = "更新用户信息",notes = "需要管理员权限才能修改")
    public ResponseEntity update(@RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        userService.update(userUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ApiOperation(value = "删除用户接口", notes = "通过用户名删除用户，需要管理员权限")
    public ResponseEntity deleteUserByUserName(@RequestParam("username") String username) {
        userService.delete(username);
        return ResponseEntity.ok().build();
    }
}