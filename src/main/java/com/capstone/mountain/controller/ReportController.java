package com.capstone.mountain.controller;

import com.capstone.mountain.Message;
import com.capstone.mountain.domain.Report;
import com.capstone.mountain.domain.Review;
import com.capstone.mountain.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/report")
    public ResponseEntity<Message> saveReport(@RequestBody Map<String, String> req){
        Long userId = Long.parseLong(req.get("user"));
        Long courseId = Long.parseLong(req.get("course"));
        String reason = req.get("reason");
        Report report = reportService.saveReport(userId, courseId, reason);
        Message message = new Message();
        message.setStatus(OK);
        message.setMessage("조회 성공");
        message.setData(report);
        return new ResponseEntity<>(message, OK);
    }
}
