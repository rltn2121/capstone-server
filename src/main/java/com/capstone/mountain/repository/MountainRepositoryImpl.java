package com.capstone.mountain.repository;

import com.capstone.mountain.domain.Mountain;
import com.capstone.mountain.dto.*;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.capstone.mountain.domain.QMountain.mountain;

@RequiredArgsConstructor
public class MountainRepositoryImpl implements MountainRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public List<MountainMainPageDto> getHotMountain() {
        return queryFactory
                .select(
                        new QMountainMainPageDto(
                                mountain.thumbnail,
                                mountain.id,
                                mountain.name
                        )
                )
                .from(mountain)
                .orderBy(orderCond())
                .limit(5)
                .fetch();
    }

    @Override
    public List<MountainNearDto> getNearMountain(double latitude, double longitude) {
        List<MountainNearDto> fetch = queryFactory
                .select(
                        new QMountainNearDto(
                                mountain.thumbnail,
                                mountain.id,
                                mountain.name,
                                mountain.latitude,
                                mountain.longitude
                        ))
                .from(mountain)
                .fetch();
        return fetch;
    }

    @Override
    public Page<MountainPreviewDto> getMountainList(Pageable pageable) {
        List<MountainPreviewDto> content = queryFactory
                .select(
                        new QMountainPreviewDto(
                                mountain.id,
                                mountain.name,
                                mountain.height,
                                mountain.location,
                                mountain.thumbnail
                        ))
                .from(mountain)
                .orderBy(orderCond())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Mountain> countQuery = queryFactory
                .selectFrom(mountain);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private OrderSpecifier<?> orderCond() {
        int month = LocalDateTime.now().getMonthValue();
        if(month >= 3 && month <= 5) {
            return mountain.spring.asc();
        }
        else if(month >= 6 && month <= 8) {
            return mountain.summer.asc();
        }
        else if(month >= 9 && month <= 11) {
            return mountain.fall.asc();
        }
        else {
            return mountain.winter.asc();
        }

    }



}
