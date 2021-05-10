package com.capstone.mountain.controller;

import com.capstone.mountain.Message;
import com.capstone.mountain.dto.CourseDetailDto;
import com.capstone.mountain.dto.RecordDetailDto;
import com.capstone.mountain.dto.RecordPreviewDto;
import com.capstone.mountain.exception.custom.NoResultException;
import com.capstone.mountain.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @GetMapping("/record")
    public ResponseEntity<Message> findRecords(@RequestParam("user") Long id){
        List<RecordPreviewDto> records = recordService.findRecords(id);
        if(records.size() == 0){
            throw new NoResultException("조회 결과 없음.");
        }
        Message message = new Message();
        message.setStatus(OK);
        message.setMessage("조회 성공");
        message.setData(records);
        return new ResponseEntity<>(message, OK);
    }

    @GetMapping("/record/{record_id}")
    public ResponseEntity<Message> findRecordDetail(@PathVariable("record_id") Long id){
        RecordDetailDto recordDetail = recordService.findRecordDetail(id);
        if(recordDetail == null){
            throw new NoResultException("조회 결과 없음.");
        }
        Message message = new Message();
        message.setStatus(OK);
        message.setMessage("조회 성공");
        message.setData(recordDetail);
        return new ResponseEntity<>(message, OK);
    }
}
