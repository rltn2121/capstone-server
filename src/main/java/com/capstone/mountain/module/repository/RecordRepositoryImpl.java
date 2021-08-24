package com.capstone.mountain.module.repository;

import com.capstone.mountain.module.model.domain.Record;
import com.capstone.mountain.module.model.dto.QRecordDetailDto;
import com.capstone.mountain.module.model.dto.QRecordPreviewDto;
import com.capstone.mountain.module.model.dto.RecordDetailDto;
import com.capstone.mountain.module.model.dto.RecordPreviewDto;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.capstone.mountain.module.model.domain.QRecord.record;

@RequiredArgsConstructor
public class RecordRepositoryImpl implements RecordRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public Page<RecordPreviewDto> findRecordPreview(Long userId, Pageable pageable) {
        List<RecordPreviewDto> content = queryFactory
                .select(new QRecordPreviewDto(
                        record.id,
                        record.title,
                        record.distance,
                        record.max_height,
                        record.moving_time_str,
                        record.calorie,
                        record.thumbnail,
                        record.date
                ))
                .from(record)
                .where(record.user.id.eq(userId))
                .orderBy(record.date.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Record> countQuery = queryFactory.selectFrom(record)
                .where(record.user.id.eq(userId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);

    }

    @Override
    public RecordDetailDto findRecordDetail(Long recordId) {
//        return null;
        return queryFactory
                .select(new QRecordDetailDto(
                        record.id,
                        record.filename,
                        record.thumbnail,
                        record.total_time_sec.longValue(),
                        record.moving_time_sec.longValue(),
                        record.distance,
                        record.avg_speed,
                        record.max_height,
                        record.calorie,
                        record.date.stringValue()
                ))
                .from(record)
                .where(record.id.eq(recordId))
                .fetchOne();
    }
}
