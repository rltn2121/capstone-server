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
                        record.id,
                        record.title,
                       record.distance,
                        record.max_height,
                        record.moving_time_str,
                        record.calorie,
                        record.thumbnail
                ))
                .from(record)
                .where(record.user.id.eq(userId))
                .fetch();

    }

    @Override
    public RecordDetailDto findRecordDetail(Long recordId) {
        return null;
//        return queryFactory
//                .select(new QRecordDetailDto(
//                        record.id,
//                        record.title,
//                        record.thumbnail,
//                        record.total_time_sec,
//                        record.moving_time_sec,
//                        record.distance,
//                        record.avg_speed,
//                        record.max_height,
//                        record.calorie,
//                        record.date
//                ))
//                .from(record)
//                .where(record.id.eq(recordId))
//                .fetchOne();
    }
}
