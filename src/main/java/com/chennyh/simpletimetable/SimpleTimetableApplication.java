package com.chennyh.simpletimetable;

import com.chennyh.simpletimetable.system.entity.Course;
import com.chennyh.simpletimetable.system.entity.Role;
import com.chennyh.simpletimetable.system.entity.User;
import com.chennyh.simpletimetable.system.entity.UserRole;
import com.chennyh.simpletimetable.system.enums.RoleType;
import com.chennyh.simpletimetable.system.repository.CourseRepository;
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
    @Autowired
    private CourseRepository courseRepository;

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
        User user = User.builder().enabled(true).email("admin@qq.com").username("root").password(bCryptPasswordEncoder.encode("root")).build();
        Course course = Course.builder().user(user).name("testCourse").teacher("teacher").room("13-201").term("2017-2018春").time("8:30").weekList("[1, 20]").day(1).start(1).step(2).build();
        userRepository.save(user);
        courseRepository.save(course);
        Role role = roleRepository.findByName(RoleType.ADMIN.getName()).get();
        userRoleRepository.save(new UserRole(user, role));
    }
}
