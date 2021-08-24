package com.capstone.mountain.module.controller;

import com.capstone.mountain.infra.response.Message;
import com.capstone.mountain.infra.response.NoDataMessage;
import com.capstone.mountain.module.model.domain.Course;
import com.capstone.mountain.module.model.domain.Record;
import com.capstone.mountain.module.model.domain.User;
import com.capstone.mountain.module.model.dto.RecordDetailDto;
import com.capstone.mountain.module.model.dto.RecordPreviewDto;
import com.capstone.mountain.infra.exception.custom.NoResultException;
import com.capstone.mountain.module.model.dto.RecordUploadDto;
import com.capstone.mountain.module.service.CourseService;
import com.capstone.mountain.module.service.RecordService;
import com.capstone.mountain.module.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.OK;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/record")
public class RecordController {
    private final RecordService recordService;
    private final UserService userService;
    private final CourseService courseService;

    /**
     * 기능: 사용자 등산 기록 조회
     * @return 사용자 등산 기록
     */
    @GetMapping
    public ResponseEntity<Message> findRecords(HttpServletRequest request, Pageable pageable){
        User user = userService.getUserFromJWT(request);
        Page<RecordPreviewDto> records = recordService.findRecords(user.getId(), pageable);
        if(records.getNumberOfElements() == 0){
            throw new NoResultException("조회 결과 없음.");
        }
        Message message = new Message(OK, "조회 성공", records);
        return new ResponseEntity<>(message, OK);
    }

    /**
     * 기능: 특정 등산 기록 조회
     * @return 특정 등산 기록
     */
    @GetMapping("/{record_id}")
    public ResponseEntity<Message> findRecordDetail(@PathVariable("record_id") Long id){
        RecordDetailDto recordDetail = recordService.findRecordDetail(id);
        if(recordDetail == null){
            throw new NoResultException("조회 결과 없음.");
        }
        Message message = new Message(OK, "조회 성공", recordDetail);
        return new ResponseEntity<>(message, OK);
    }

    /**
     * 기능: 등산 기록 업로드
     * @return 없음
     */
    // 참고: 스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술 (HTTP 요청 메시지 - JSON)
    // 리팩토링 이전: 23개 파라미터를 직접 받아서, recordService.uploadRecord()에 전달
    // 리팩토링 이후: 23개 파라미터를 RecordUploadDto로 한 번에 받아서 Record 객체 생성 후 recordService.uploadRecord()에 전달
    @PostMapping
    public ResponseEntity<NoDataMessage> uploadRecord(HttpServletRequest request, @RequestBody RecordUploadDto recordUploadDto) {
        User user = userService.getUserFromJWT(request);
        Course course = courseService.findById(recordUploadDto.getCourse());
        
        Record record = new Record(user, course, recordUploadDto);
        recordService.uploadRecord(record);
        
        NoDataMessage message = new NoDataMessage("OK", "추가 성공");
        return new ResponseEntity<>(message, OK);
    }
}

