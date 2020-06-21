package com.chennyh.simpletimetable.security.service;

import com.chennyh.simpletimetable.security.entity.JwtUser;
import com.chennyh.simpletimetable.system.entity.User;
import com.chennyh.simpletimetable.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * @author shuang.kou
 * @description UserDetailsService实现类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userService.find(name);
        return new JwtUser(user);
    }

}
