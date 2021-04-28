//package com.capstone.mountain.repository;
//import com.capstone.mountain.dto.CoursePreviewDto;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//
//@SpringBootTest
//@Transactional
//class CourseRepositoryTest {
//    @Autowired
//    CourseRepository courseRepository;
//    @Autowired
//    EntityManager em;
//
//    @Test
//    public void 등산로찾기2() throws Exception{
//        // given
//        String keyword = "해파랑길";
//        String cond = "distance";
//
//        // when
//        List<CoursePreviewDto> result = courseRepository.searchCourses(keyword, cond);
//
//        // then
//        Assertions.assertThat(result.size()).isEqualTo(2);
//    }
//}