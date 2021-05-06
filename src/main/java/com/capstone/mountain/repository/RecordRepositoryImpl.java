package com.capstone.mountain.repository;

import com.capstone.mountain.domain.QRecord;
import com.capstone.mountain.dto.QRecordDetailDto;
import com.capstone.mountain.dto.QRecordPreviewDto;
import com.capstone.mountain.dto.RecordDetailDto;
import com.capstone.mountain.dto.RecordPreviewDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.capstone.mountain.domain.QRecord.record;

@RequiredArgsConstructor
public class RecordRepositoryImpl implements RecordRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public List<RecordPreviewDto> findRecordPreview(Long userId) {
        return queryFactory
                .select(new QRecordPreviewDto(
                        record.name,
                       record.distance,
                        record.height,
                        record.duration,
                        record.calorie,
                        record.thumbnail
                ))
                .from(record)
                .where(record.user.id.eq(userId))
                .fetch();

    }

    @Override
    public RecordDetailDto findRecordDetail(Long recordId) {
        return queryFactory
                .select(new QRecordDetailDto(
                        record.name,
                        record.thumbnail,
                        record.duration,
                        record.movingTime,
                        record.distance,
                        record.speed,
                        record.height,
                        record.calorie,
                        record.date
                ))
                .from(record)
                .where(record.id.eq(recordId))
                .fetchOne();
    }
}
