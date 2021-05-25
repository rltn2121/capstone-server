package com.capstone.mountain.repository;

import com.capstone.mountain.dto.RecordDetailDto;
import com.capstone.mountain.dto.RecordPreviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecordRepositoryCustom {
    Page<RecordPreviewDto> findRecordPreview(Long userId, Pageable pageable);
    RecordDetailDto findRecordDetail(Long recordId);
}
