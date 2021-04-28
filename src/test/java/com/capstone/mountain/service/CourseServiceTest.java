//package com.capstone.mountain.service;
//
//import com.capstone.mountain.dto.CoursePreviewDto;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class CourseServiceTest {
//
//    @Autowired
//    CourseService courseService;
//
//    @Test
//    public void 검색() throws Exception{
//        // given
//        String keyword = "해파랑길";
//        String order = "distance";
//        // when
//
//        List<CoursePreviewDto> coursePreviewDtos = courseService.searchCourses(keyword, order);
//        // then
//        Assertions.assertThat(coursePreviewDtos.size()).isEqualTo(2);
//    }
//}