package com.capstone.mountain.module.repository;

import com.capstone.mountain.module.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    User findByUsername(String username);
    List<User> findByNickname(String nickname);
}
