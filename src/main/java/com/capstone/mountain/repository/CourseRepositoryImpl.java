package com.capstone.mountain.repository;

import com.capstone.mountain.domain.Course;
import com.capstone.mountain.domain.QRecommendCourse;
import com.capstone.mountain.domain.RecommendCourse;
import com.capstone.mountain.dto.*;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.capstone.mountain.domain.QCourse.course;
import static com.capstone.mountain.domain.QHotCourse.hotCourse;
import static com.capstone.mountain.domain.QRecommendCourse.recommendCourse;

@RequiredArgsConstructor
public class CourseRepositoryImpl implements CourseRepositoryCustom{
    private final JPAQueryFactory queryFactory;


    @Override
    public List<CourseRecommendDto> getRecommendCourseMain(Long userId) {
        List<CourseRecommendDto> fetch = queryFactory
                .select(
                        new QCourseRecommendDto(
                                course.id,
                                course.title,
                                course.distance,
                                course.moving_time_str,
                                course.difficulty,
                                course.thumbnail
                        )
                )
                .from(recommendCourse)
                .leftJoin(recommendCourse.course, course)
                .where(recommendCourse.user.id.eq(userId))
                .orderBy(Expressions.numberTemplate(Long.class, "function('rand')").asc())
                .limit(20)
                .fetch();
        return fetch;
    }

    @Override
    public Page<CoursePreviewDto> getRecommendCourseDetail(Long userId, Pageable pageable) {
        List<CoursePreviewDto> content = queryFactory
                .select(
                        new QCoursePreviewDto(
                                course.id,
                                course.title,
                                course.distance,
                                course.ele_dif,
                                course.moving_time_str,
                                course.difficulty,
                                course.gpx_url,
                                course.thumbnail
                        )
                )
                .from(recommendCourse)
                .leftJoin(recommendCourse.course, course)
                .where(recommendCourse.user.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // count query 최적화
        // 마지막 페이지일 경우 countQuery 호출 안함
        JPAQuery<RecommendCourse> countQuery = queryFactory
                .selectFrom(recommendCourse)
                .where(recommendCourse.user.id.eq(userId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    @Override
    public Page<CoursePreviewDto> searchCourses(String keyword, String cond, Pageable pageable) {
        List<CoursePreviewDto> content = queryFactory
                .select(
                        new QCoursePreviewDto(
                                course.id,
                                course.title,
                                course.distance,
                                course.ele_dif,
                                course.moving_time_str,
                                course.difficulty,
                                course.gpx_url,
                                //review.count(),
                                //review.score.avg(),
                                course.thumbnail
                        )
                )
                .from(course)
             //   .from(review)
             //   .rightJoin(review.course, course)
                .where(
                        course.title.contains(keyword)
                                .or(course.location.contains(keyword))
                )
             //   .groupBy(course.id)
                .orderBy(orderCond(cond))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // count query 최적화
        // 마지막 페이지일 경우 countQuery 호출 안함
        JPAQuery<Course> countQuery = queryFactory
                .selectFrom(course)
                .where(
                        course.title.contains(keyword)
                                .or(course.location.contains(keyword))
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);

    }


    @Override
    public CourseDetailDto findCourseDetail(Long courseId) {
        return queryFactory
                .select(
                        new QCourseDetailDto(
                                course.id,
                                course.title,
                                course.location,
                                course.distance,
                                course.moving_time_str,
                                course.total_time_str,
                                course.avg_speed,
                                course.avg_pace,
                                course.max_height,
                                course.min_height,
                                course.ele_dif,
                                course.total_uphill,
                                course.total_downhill,
                                course.difficulty,
                                course.thumbnail,
                                course.gpx_url,
                                course.date
//                                review.count(),
//                                review.score.avg(),
                        )
                )
                .from(course)
//                .from(review)
//                .rightJoin(review.course, course)
                .where(course.id.eq(courseId))
                .fetchOne();
    }

    private OrderSpecifier<?> orderCond(String cond) {
        if(cond.equals("difficulty")) {
            return course.difficulty.asc();
        }
        else if(cond.equals("height")){
            return course.max_height.asc();
        }
        else if(cond.equals("distance")){
            return course.distance.asc();
        }
        else
            return course.id.asc();
    }
}
