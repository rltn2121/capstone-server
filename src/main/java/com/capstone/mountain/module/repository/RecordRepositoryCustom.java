package com.capstone.mountain.module.repository;

import com.capstone.mountain.module.model.dto.RecordDetailDto;
import com.capstone.mountain.module.model.dto.RecordPreviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecordRepositoryCustom {
    Page<RecordPreviewDto> findRecordPreview(Long userId, Pageable pageable);
    RecordDetailDto findRecordDetail(Long recordId);
}
