package com.capstone.mountain.module.controller;

import com.capstone.mountain.infra.response.NoDataMessage;
import com.capstone.mountain.module.model.domain.Course;
import com.capstone.mountain.module.model.domain.Report;
import com.capstone.mountain.module.model.domain.User;
import com.capstone.mountain.infra.exception.custom.DataAlreadyExistException;
import com.capstone.mountain.module.model.dto.ReportSaveDto;
import com.capstone.mountain.module.service.CourseService;
import com.capstone.mountain.module.service.ReportService;
import com.capstone.mountain.module.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;
    private final UserService userService;
    private final CourseService courseService;

    /**
     * 기능: 잘못된 등산로 신고하기
     * @return 없음
     */
    @PostMapping
    public ResponseEntity<NoDataMessage> saveReport(HttpServletRequest request, @RequestBody ReportSaveDto reportSaveDto){
        User user = userService.getUserFromJWT(request);
        Course course = courseService.findById(reportSaveDto.getCourse());
        String reason = reportSaveDto.getReason();

        // 해당 사용자가 이 등산로를 이미 신고했는지 확인
        Report find = reportService.findReport(user, course);
        if(find != null){
            throw new DataAlreadyExistException("이미 신고했습니다.");
        }

        // 신고 하지 않았다면 신고하기
        Report report = reportService.saveReport(user, course, reason);
        NoDataMessage message = new NoDataMessage("OK", "신고 성공");
        return new ResponseEntity<>(message, OK);
    }
}
