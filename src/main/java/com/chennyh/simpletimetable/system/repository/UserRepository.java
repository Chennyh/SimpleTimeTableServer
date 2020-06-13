package com.chennyh.simpletimetable.system.repository;

import com.chennyh.simpletimetable.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author shuang.kou
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void deleteByUsername(String username);
}
