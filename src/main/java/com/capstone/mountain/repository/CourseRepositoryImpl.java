package com.capstone.mountain.repository;

import com.capstone.mountain.dto.CoursePreviewDto;
import com.capstone.mountain.dto.QCoursePreviewDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.capstone.mountain.domain.QCourse.course;
import static com.capstone.mountain.domain.QReview.review;

@RequiredArgsConstructor
public class CourseRepositoryImpl implements CourseRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<CoursePreviewDto> searchCourses(String keyword, String cond) {
        return queryFactory
                .select(
                        new QCoursePreviewDto(
                                course.id,
                                course.name,
                                course.distance,
                                course.height,
                                course.time,
                                course.difficulty,
                                course.url,
                                review.count(),
                                review.score.avg()
                        )
                )
                .from(review)
                .rightJoin(review.course, course)
                .where(course.name.contains(keyword))
                .groupBy(course.id)
                .orderBy(orderCond(cond))
                .fetch();
    }

    private OrderSpecifier<?> orderCond(String cond) {

        System.out.println("이거진짜 ㄹㅇ 제대로 전달된거임 cond = " + cond);
        if(cond.equals("difficulty")) {
            return course.difficulty.asc();
        }
        else if(cond.equals("height")){
            return course.height.asc();
        }
        else if(cond.equals("distance")){
            return course.distance.asc();
        }
        else{
            System.out.println("아무것도 전달된게 없어요 그래서 null임 ㅅㄱ");
            return null;
        }
    }
}
