package com.capstone.mountain.service;

import com.capstone.mountain.domain.Record;
import com.capstone.mountain.domain.User;
import com.capstone.mountain.dto.RecordDetailDto;
import com.capstone.mountain.dto.RecordPreviewDto;
import com.capstone.mountain.repository.RecordRepository;
import com.capstone.mountain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;

    public List<RecordPreviewDto> findRecords(Long userId){
        return recordRepository.findRecordPreview(userId);
    }

    public RecordDetailDto findRecordDetail(Long recordId) {
        return recordRepository.findRecordDetail(recordId);
    }
}
