package com.capstone.mountain.repository;

import com.capstone.mountain.domain.Course;
import com.capstone.mountain.domain.QFavorite;
import com.capstone.mountain.domain.RecommendCourse;
import com.capstone.mountain.domain.Record;
import com.capstone.mountain.dto.*;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.capstone.mountain.domain.QCourse.course;
import static com.capstone.mountain.domain.QFavorite.favorite;
import static com.capstone.mountain.domain.QRecommendCourse.recommendCourse;
import static com.capstone.mountain.domain.QRecord.record;

@RequiredArgsConstructor
public class CourseRepositoryImpl implements CourseRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<CourseMainPageDto> getRecommendCourseMain(Long userId) {
        List<CourseMainPageDto> fetch = queryFactory
                .select(
                        new QCourseMainPageDto(
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
                .limit(10)
                .fetch();
        return fetch;
    }

    @Override
    public List<CourseMainPageDto> getHotCourseMain() {
        return queryFactory
                .select(
                        new QCourseMainPageDto(
                                course.id,
                                course.title,
                                course.distance,
                                course.moving_time_str,
                                course.difficulty,
                                course.thumbnail
                        )
                )
                .from(record)
                .leftJoin(record.course, course)
                .where(
                        record.date.after(LocalDateTime.now().minusMonths(12))
                )
                .groupBy(record.course)
                .orderBy(record.course.count().desc())
                .limit(10)
                .fetch();
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
    public Page<CoursePreviewDto> getHotCourseDetail(Pageable pageable) {
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
                .from(record)
                .leftJoin(record.course, course)
                .where(
                        record.date.after(LocalDateTime.now().minusMonths(24))
                )
                .groupBy(record.course)
                .orderBy(record.course.count().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // count query 최적화
        // 마지막 페이지일 경우 countQuery 호출 안함
        JPAQuery<Record> countQuery = queryFactory
                .selectFrom(record)
                .where(record.date.after(LocalDateTime.now().minusMonths(24)))
                .groupBy(record.course);

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
    public CourseDetailDto findCourseDetail(Long courseId, Long userId) {


        CourseDetailDto courseDetailDto = queryFactory
                .select(new QCourseDetailDto(
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
                        course.date,
                        favorite.status
                ))
                .from(favorite)
                .rightJoin(favorite.course, course)
                .groupBy(course.id, favorite.status)
                .having(course.id.eq(courseId))
                .fetchOne();
        return courseDetailDto;


        //        select *
//                from course a
//        left join favorite b on a.course_id = b.course_id
//        group by a.course_id, status
//        having a.course_id = 1
//        return queryFactory
//                .select(
//                        new QCourseDetailDto(
//                                course.id,
//                                course.title,
//                                course.location,
//                                course.distance,
//                                course.moving_time_str,
//                                course.total_time_str,
//                                course.avg_speed,
//                                course.avg_pace,
//                                course.max_height,
//                                course.min_height,
//                                course.ele_dif,
//                                course.total_uphill,
//                                course.total_downhill,
//                                course.difficulty,
//                                course.thumbnail,
//                                course.gpx_url,
//                                course.date
////                                review.count(),
////                                review.score.avg(),
//                        )
//                )
//                .from(course)
////                .from(review)
////                .rightJoin(review.course, course)
//                .where(course.id.eq(courseId))
//                .fetchOne();
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
