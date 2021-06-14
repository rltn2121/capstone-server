package com.capstone.mountain.repository;

import com.capstone.mountain.domain.*;
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
import static com.capstone.mountain.domain.QFavorite.favorite;

@RequiredArgsConstructor
public class FavoriteRepositoryImpl implements FavoriteRepositoryCustom{
    private final JPAQueryFactory queryFactory;


    @Override
    public Page<CoursePreviewDto> findByUser(User user, Pageable pageable) {
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
                .from(favorite)
                .leftJoin(favorite.course, course)
                .where(favorite.user.eq(user)
                .and(favorite.status.eq(true))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Favorite> countQuery = queryFactory.selectFrom(favorite)
                .where(favorite.user.eq(user));
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    @Override
    public Favorite isFavoriteExist(User user, Course course) {
        return queryFactory
                .selectFrom(favorite)
                .where(favorite.user.eq(user)
                        .and(favorite.course.eq(course))
                )
                .fetchOne();
    }
    @Override
    public long toggleFavorite(User user, Course course, boolean status) {
        return queryFactory
                .update(favorite)
                .set(favorite.status, status)
                .where(favorite.user.eq(user)
                        .and(favorite.course.eq(course))
                )
                .execute();
    }

    @Override
    public boolean getFavoriteStatus(Long userId, Long courseId) {
        Boolean aBoolean = queryFactory
                .select(favorite.status)
                .from(favorite)
                .where(favorite.user.id.eq(userId)
                        .and(course.id.eq(courseId)))
                .fetchOne();
        return aBoolean;
    }


}
