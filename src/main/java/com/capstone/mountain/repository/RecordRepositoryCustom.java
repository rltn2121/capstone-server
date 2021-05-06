package com.capstone.mountain.repository;

import com.capstone.mountain.dto.RecordDetailDto;
import com.capstone.mountain.dto.RecordPreviewDto;

import java.util.List;

public interface RecordRepositoryCustom {
    List<RecordPreviewDto> findRecordPreview(Long userId);
    RecordDetailDto findRecordDetail(Long recordId);
}
