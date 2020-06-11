package com.chennyh.simpletimetable;

import com.chennyh.simpletimetable.system.entity.Role;
import com.chennyh.simpletimetable.system.entity.User;
import com.chennyh.simpletimetable.system.entity.UserRole;
import com.chennyh.simpletimetable.system.enums.RoleType;
import com.chennyh.simpletimetable.system.repository.RoleRepository;
import com.chennyh.simpletimetable.system.repository.UserRepository;
import com.chennyh.simpletimetable.system.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author shuang.kou
 */
@SpringBootApplication
public class SimpleTimetableApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    public static void main(java.lang.String[] args) {
        SpringApplication.run(SimpleTimetableApplication.class, args);
    }

    @Override
    public void run(java.lang.String... args) {
        //初始化角色信息
        for (RoleType roleType : RoleType.values()) {
            roleRepository.save(new Role(roleType.getName(), roleType.getDescription()));
        }
        //初始化一个 admin 用户
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = User.builder().enabled(true).fullName("admin").userName("root").password(bCryptPasswordEncoder.encode("root")).build();
        userRepository.save(user);
        Role role = roleRepository.findByName(RoleType.ADMIN.getName()).get();
        userRoleRepository.save(new UserRole(user, role));
    }
}
