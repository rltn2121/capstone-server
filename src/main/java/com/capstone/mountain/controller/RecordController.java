package com.capstone.mountain.controller;

import com.capstone.mountain.Message;
import com.capstone.mountain.domain.User;
import com.capstone.mountain.dto.CourseDetailDto;
import com.capstone.mountain.dto.RecordDetailDto;
import com.capstone.mountain.dto.RecordPreviewDto;
import com.capstone.mountain.exception.custom.NoResultException;
import com.capstone.mountain.service.RecordService;
import com.capstone.mountain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;
    private final UserService userService;

    @GetMapping("/record")
    public ResponseEntity<Message> findRecords(HttpServletRequest request, Pageable pageable){
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        User user = userService.getUserFromJWT(jwtToken);
        Page<RecordPreviewDto> records = recordService.findRecords(user.getId(), pageable);
        if(records.getNumberOfElements() == 0){
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
