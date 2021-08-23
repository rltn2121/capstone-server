package com.capstone.mountain.controller;

import com.capstone.mountain.domain.Course;
import com.capstone.mountain.domain.Report;
import com.capstone.mountain.domain.User;
import com.capstone.mountain.exception.custom.DataAlreadyExistException;
import com.capstone.mountain.service.CourseService;
import com.capstone.mountain.service.ReportService;
import com.capstone.mountain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final UserService userService;
    private final CourseService courseService;

    @PostMapping("/report")
    public Map<String, String> saveReport(HttpServletRequest request, @RequestBody Map<String, String> req){
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        User user = userService.getUserFromJWT(jwtToken);
        Long courseId = Long.parseLong(req.get("course"));
        Course course = courseService.findById(courseId);
        String reason = req.get("reason");
        Report find = reportService.findReport(user, course);
        if(find != null){
            throw new DataAlreadyExistException("이미 신고했습니다.");
        }
        Report report = reportService.saveReport(user, course, reason);
        Map<String, String> message = new HashMap<>();
        message.put("status", "OK");
        message.put("message", "신고 성공");
        return message;
    }
}
