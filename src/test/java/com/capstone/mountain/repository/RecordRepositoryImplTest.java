//package com.capstone.mountain.repository;
//
//import com.capstone.mountain.module.model.domain.Record;
//import com.capstone.mountain.module.model.dto.RecordPreviewDto;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class RecordRepositoryImplTest {
//    @Autowired
//    RecordRepository recordRepository;
//
//    @Test
//    public void 조회() throws Exception{
//        // given
//        Long userId = 1L;
//        // when
//        List<RecordPreviewDto> recordPreview = recordRepository.findRecordPreview(userId);
//
//        for (RecordPreviewDto recordPreviewDto : recordPreview) {
//            System.out.println("recordPreviewDto = " + recordPreviewDto);
//        }
//        // then
//        Assertions.assertThat(recordPreview.size()).isEqualTo(8);
//    }
//}