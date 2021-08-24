//package com.capstone.mountain.repository;
//
//import com.capstone.mountain.module.model.domain.Course;
//import com.capstone.mountain.module.model.domain.Favorite;
//import com.capstone.mountain.module.model.domain.User;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class FavoriteRepositoryImplTest {
//
//    @Autowired
//    FavoriteRepository favoriteRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    CourseRepository courseRepository;
//
//    @Test
//    public void 있는지확인() throws Exception{
//        // given
//        Long courseId = 4L;
//        Long userId = 0L;
//
//        User user = userRepository.findById(userId).get();
//        Course course = courseRepository.findById(courseId).get();
//        // when
//        Favorite favoriteExist = favoriteRepository.isFavoriteExist(user, course);
//        // then
//        assertThat(favoriteExist).isEqualTo(null);
////        assertThat(favoriteExist.getCourse()).isEqualTo(course);
////        assertThat(favoriteExist.getUser()).isEqualTo(user);
////        assertThat(favoriteExist.isStatus()).isEqualTo(true);
//    }
//}