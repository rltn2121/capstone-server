package com.capstone.mountain.service;

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

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;

    public Page<RecordPreviewDto> findRecords(Long userId, Pageable pageable){
        return recordRepository.findRecordPreview(userId, pageable);
    }

    public RecordDetailDto findRecordDetail(Long recordId) {
        return recordRepository.findRecordDetail(recordId);
    }
}
