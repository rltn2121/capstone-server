package com.capstone.mountain.repository;

import com.capstone.mountain.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    User findByUsername(String username);
    List<User> findByNickname(String nickname);
}
