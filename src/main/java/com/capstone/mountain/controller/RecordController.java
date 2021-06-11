package com.capstone.mountain.controller;

import com.capstone.mountain.Message;
import com.capstone.mountain.domain.Course;
import com.capstone.mountain.domain.User;
import com.capstone.mountain.dto.CourseDetailDto;
import com.capstone.mountain.dto.RecordDetailDto;
import com.capstone.mountain.dto.RecordPreviewDto;
import com.capstone.mountain.exception.custom.NoResultException;
import com.capstone.mountain.service.CourseService;
import com.capstone.mountain.service.RecordService;
import com.capstone.mountain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;
    private final UserService userService;
    private final CourseService courseService;

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

    @PostMapping("/record")
    public Map<String, String> uploadRecord(HttpServletRequest request, @RequestBody Map<String, String> req) {
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        User user = userService.getUserFromJWT(jwtToken);
        Long courseId = Long.parseLong(req.get("course"));
        Course course = courseService.findById(courseId);
        String title = req.get("title");
        String filename = req.get("filename");
        System.out.println("LocalDateTime.now() = " + LocalDateTime.now());
        double distance = Double.parseDouble(req.get("distance"));
        String moving_time_str = req.get("moving_time_str");
        String total_time_str = req.get("total_time_str");
        int moving_time_sec = Integer.parseInt(req.get("moving_time_sec"));
        int total_time_sec = Integer.parseInt(req.get("total_time_sec"));
        double avg_speed = Double.parseDouble(req.get("avg_speed"));
        double avg_pace = Double.parseDouble(req.get("avg_pace"));
        String location = req.get("location");
        double latitude = Double.parseDouble(req.get("latitude"));
        double longitude = Double.parseDouble(req.get("longitude"));
        int max_height = Integer.parseInt(req.get("max_height"));
        int min_height = Integer.parseInt(req.get("min_height"));
        int ele_dif = Integer.parseInt(req.get("ele_dif"));
        int total_uphill = Integer.parseInt(req.get("total_uphill"));
        int total_downhill = Integer.parseInt(req.get("total_downhill"));
        String difficulty = req.get("difficulty");
        int calorie = Integer.parseInt(req.get("calorie"));
        LocalDateTime date = LocalDateTime.parse(req.get("date"));
        String gpx_url = req.get("gpx_url");
        String thumbnail = req.get("thumbnail");


        recordService.uploadRecord( user,
                course,
                title,
                filename,
                distance,
                moving_time_str,
                total_time_str,
                moving_time_sec,
                total_time_sec,
                avg_speed,
                avg_pace,
                location,
                latitude,
                longitude,
                max_height,
                min_height,
                ele_dif,
                total_uphill,
                total_downhill,
                difficulty,
                calorie,
                date,
                gpx_url,
                thumbnail);

        Map<String, String> message = new HashMap<>();
        message.put("status", "OK");
        message.put("message", "추가 성공");
        return message;
    }
}

