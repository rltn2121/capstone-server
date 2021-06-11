package com.capstone.mountain.service;

import com.capstone.mountain.domain.Course;
import com.capstone.mountain.domain.Record;
import com.capstone.mountain.domain.User;
import com.capstone.mountain.dto.RecordDetailDto;
import com.capstone.mountain.dto.RecordPreviewDto;
import com.capstone.mountain.repository.RecordRepository;
import com.capstone.mountain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;
    private final CourseService courseService;

    public Page<RecordPreviewDto> findRecords(Long userId, Pageable pageable){
        return recordRepository.findRecordPreview(userId, pageable);
    }

    public RecordDetailDto findRecordDetail(Long recordId) {
        return recordRepository.findRecordDetail(recordId);
    }

    public Record uploadRecord(User user, Course course, String title, String filename, Double distance, String moving_time_str, String total_time_str, int moving_time_sec, int total_time_sec, Double avg_speed, Double avg_pace, String location, Double latitude, Double longitude, int max_height, int min_height, int ele_dif, int total_uphill, int total_downhill, String difficulty, int calorie, LocalDateTime date, String gpx_url, String thumbnail){
        Record record = new Record(
                user,
                course,
                title,
                filename,
                distance,
                moving_time_str,
                total_time_str,
                moving_time_sec,
                total_time_sec,
                avg_speed,
                avg_pace,
                location,
                latitude,
                longitude,
                max_height,
                min_height,
                ele_dif,
                total_uphill,
                total_downhill,
                difficulty,
                calorie,
                date,
                gpx_url,
                thumbnail);
        return recordRepository.save(record);
    }
}
