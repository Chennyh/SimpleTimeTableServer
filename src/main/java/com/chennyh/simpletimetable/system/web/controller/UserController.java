package com.chennyh.simpletimetable.system.web.controller;

import com.chennyh.simpletimetable.system.entity.User;
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
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private final CurrentUserUtils currentUserUtils;

    @PostMapping()
    @ApiOperation(value = "用户注册接口", notes = "用户使用此接口进行注册操作")
    public ResponseEntity signUp(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        System.out.println("用户注册接口被访问");
        userService.save(userRegisterRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @ApiOperation(value = "获取用户列表", notes = "需要登录，否则会获取失败")
    public ResponseEntity<Page<UserRepresentation>> getAllUser(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        System.out.println("当前访问获取用户列表接口的用户为：" + currentUserUtils.getCurrentUser().getUsername());
        Page<UserRepresentation> allUser = userService.getAll(pageNum, pageSize);
        return ResponseEntity.ok().body(allUser);
    }

    @GetMapping("/i")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @ApiOperation(value = "获取当前用户信息", notes = "需要token")
    public ResponseEntity getCurrentUser() {
        User user = currentUserUtils.getCurrentUser();
        return ResponseEntity.ok().body(user.toUserRepresentation());
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    @ApiOperation(value = "更新用户信息", notes = "需要管理员权限才能修改")
    public ResponseEntity update(@RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        System.out.println("当前访问更新用户信息接口的用户为：" + currentUserUtils.getCurrentUser().getUsername());
        userService.update(userUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ApiOperation(value = "删除用户接口", notes = "通过用户名删除用户，需要管理员权限")
    public ResponseEntity deleteUserByUserName(@PathVariable String username) {
        System.out.println("当前访问删除用户接口的用户为：" + currentUserUtils.getCurrentUser().getUsername());
        userService.delete(username);
        return ResponseEntity.ok().build();
    }
}
