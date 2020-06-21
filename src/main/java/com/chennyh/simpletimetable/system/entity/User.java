package com.chennyh.simpletimetable.system.entity;

import com.chennyh.simpletimetable.system.web.representation.UserRepresentation;
import com.chennyh.simpletimetable.system.web.request.UserRegisterRequest;
import com.chennyh.simpletimetable.system.web.request.UserUpdateRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author shuang.kou
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User extends AbstractAuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(columnDefinition = "tinyint(1) default 1")
    private Boolean enabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<UserRole> userRoles = new ArrayList<>();

    public List<SimpleGrantedAuthority> getRoles() {
        List<Role> roles = userRoles.stream().map(UserRole::getRole).collect(Collectors.toList());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
        return authorities;
    }

    public UserRepresentation toUserRepresentation() {
        return UserRepresentation.builder().email(this.email)
                .username(this.username)
                .id(this.id).build();
    }

    public static User of(UserRegisterRequest userRegisterRequest) {
        return User.builder().email(userRegisterRequest.getEmail())
                .username(userRegisterRequest.getUsername())
                .enabled(true).build();
    }

    public void updateFrom(UserUpdateRequest userUpdateRequest) {
        if (Objects.nonNull(userUpdateRequest.getEmail())) {
            this.setEmail(userUpdateRequest.getEmail());
        }
        if (Objects.nonNull(userUpdateRequest.getPassword())) {
            this.setPassword(new BCryptPasswordEncoder().encode(userUpdateRequest.getPassword()));
        }
        if (Objects.nonNull(userUpdateRequest.getEnabled())) {
            this.setEnabled(userUpdateRequest.getEnabled());
        }
    }

}
