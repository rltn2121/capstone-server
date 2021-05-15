package com.capstone.mountain.repository;

import com.capstone.mountain.domain.Course;
import com.capstone.mountain.dto.CourseDetailDto;
import com.capstone.mountain.dto.CoursePreviewDto;
import com.capstone.mountain.dto.QCourseDetailDto;
import com.capstone.mountain.dto.QCoursePreviewDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.capstone.mountain.domain.QCourse.course;
import static com.capstone.mountain.domain.QReview.review;

@RequiredArgsConstructor
public class CourseRepositoryImpl implements CourseRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CoursePreviewDto> searchCourses(String keyword, String cond, Pageable pageable) {
        List<CoursePreviewDto> content = queryFactory
                .select(
                        new QCoursePreviewDto(
                                course.id,
                                course.title,
                                course.distance,
                                course.max_height,
                                course.moving_time,
                                course.difficulty,
                                course.gpx_url,
                                review.count(),
                                review.score.avg(),
                                course.thumbnail
                        )
                )
                .from(review)
                .rightJoin(review.course, course)
                .where(
                        course.title.contains(keyword)
                                .or(course.location.contains(keyword))
                )
                .groupBy(course.id)
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
                                course.moving_time,
                                course.avg_speed,
                                course.max_height,
                                course.difficulty,
                                course.gpx_url,
                                review.count(),
                                review.score.avg(),
                                course.thumbnail
                        )
                )
                .from(review)
                .rightJoin(review.course, course)
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
