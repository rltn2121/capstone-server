package com.capstone.mountain.repository;
import com.capstone.mountain.dto.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


@SpringBootTest
@Transactional
class CourseRepositoryTest {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    public void 추천목록() throws Exception{
        // given
        Long userId = 1L;

        // when
        List<CourseRecommendDto> recommendCourse = courseRepository.getRecommendCourseMain(userId);

        // then
        Assertions.assertThat(recommendCourse.size()).isEqualTo(100);

    }
//    @Test
//    public void 등산로찾기2() throws Exception{
//        // given
//        String keyword = "해파랑길";
//        String cond = "distance";
//        // when
//        List<CoursePreviewDto> result = courseRepository.searchCourses(keyword, cond);
//
//        // then
//        Assertions.assertThat(result.size()).isEqualTo(2);
//    }//
//
//    @Test
//    public void 등산로상세정보() throws Exception{
//        // given
//        Long course_id = 1L;
//        // when
//        CourseDetailDto courseDetailDto = queryFactory
//                .select(
//                        new QCourseDetailDto(
//                                course.id,
//                                course.name,
//                                course.location,
//                                course.distance,
//                                course.time,
//                                course.speed,
//                                course.height,
//                                course.difficulty,
//                                course.url,
//                                review.count(),
//                                review.score.avg()
//                        )
//                )
//                .from(review)
//                .rightJoin(review.course, course)
//                .where(course.id.eq(course_id))
//                .fetchOne();
//        // then
//        Assertions.assertThat(courseDetailDto.getName()).isEqualTo("210421 자굴산~한우산");
//    }
}