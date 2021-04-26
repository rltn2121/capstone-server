//package com.capstone.mountain.repository;
//
//import com.capstone.mountain.domain.User;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class UserRepositoryTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void findUser() throws Exception{
//        // given
//        String username = "test2";
//        // when
//        User byUsername = userRepository.findByUsername(username);
//        // then
//        Assertions.assertThat(byUsername.getUsername()).isEqualTo(username);
//
//    }
//}