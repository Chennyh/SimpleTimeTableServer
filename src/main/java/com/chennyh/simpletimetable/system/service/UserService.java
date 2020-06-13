package com.chennyh.simpletimetable.system.service;

import com.chennyh.simpletimetable.system.entity.Role;
import com.chennyh.simpletimetable.system.entity.User;
import com.chennyh.simpletimetable.system.exception.ResourceNotFoundException;
import com.chennyh.simpletimetable.system.exception.UserNameAlreadyExistException;
import com.chennyh.simpletimetable.system.repository.RoleRepository;
import com.chennyh.simpletimetable.system.repository.UserRepository;
import com.chennyh.simpletimetable.system.repository.UserRoleRepository;
import com.chennyh.simpletimetable.system.web.representation.UserRepresentation;
import com.chennyh.simpletimetable.system.web.request.UserRegisterRequest;
import com.chennyh.simpletimetable.system.web.request.UserUpdateRequest;
import com.google.common.collect.ImmutableMap;
import com.chennyh.simpletimetable.system.entity.UserRole;
import com.chennyh.simpletimetable.system.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author shuang.kou
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    public static final String USERNAME = "username";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public void save(UserRegisterRequest userRegisterRequest) {
        checkUserNameNotExist(userRegisterRequest.getUsername());
        User user = User.of(userRegisterRequest);
        user.setPassword(bCryptPasswordEncoder.encode(userRegisterRequest.getPassword()));
        userRepository.save(user);
        //给用户绑定两个角色：用户和管理者
        Role studentRole = roleRepository.findByName(RoleType.USER.getName()).orElseThrow(() -> new ResourceNotFoundException(ImmutableMap.of("roleName", RoleType.USER.getName())));
        Role managerRole = roleRepository.findByName(RoleType.MANAGER.getName()).orElseThrow(() -> new ResourceNotFoundException(ImmutableMap.of("roleName", RoleType.MANAGER.getName())));
        userRoleRepository.save(new UserRole(user, studentRole));
        userRoleRepository.save(new UserRole(user, managerRole));
    }

    public User find(String userName) {
        return userRepository.findByUsername(userName).orElseThrow(() -> new ResourceNotFoundException(ImmutableMap.of(USERNAME, userName)));
    }

    public void update(UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findByUsername(userUpdateRequest.getUsername()).orElseThrow(() -> new ResourceNotFoundException(ImmutableMap.of(USERNAME, userUpdateRequest.getUsername())));
        user.updateFrom(userUpdateRequest);
        userRepository.save(user);
    }

    public void delete(String userName) {
        userRepository.deleteByUsername(userName);
    }

    public Page<UserRepresentation> getAll(int pageNum, int pageSize) {
        return userRepository.findAll(PageRequest.of(pageNum, pageSize)).map(User::toUserRepresentation);
    }

    private void checkUserNameNotExist(String userName) {
        boolean exist = userRepository.findByUsername(userName).isPresent();
        if (exist) {
            throw new UserNameAlreadyExistException(ImmutableMap.of(USERNAME, userName));
        }
    }
}
