package com.capstone.mountain.controller;

import com.capstone.mountain.Hello;
import com.capstone.mountain.Message;
import com.capstone.mountain.domain.Mountain;
import com.capstone.mountain.dto.CourseDetailDto;
import com.capstone.mountain.dto.CoursePreviewDto;
import com.capstone.mountain.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
//
//    @GetMapping("/hello")
//    public ResponseEntity<Message> hello(){
//        //return new Hello("hello");
//        Message message = new Message();
//        message.setStatus(OK);
//        message.setMessage("전송 성공");
//        message.setData(new Hello("hello"));
//        return new ResponseEntity<>(message, OK);
//    }

    @GetMapping("/course/{course_id}")
    public ResponseEntity<Message> findCourseDetail(@PathVariable("course_id") Long id){
        CourseDetailDto courseDetail = courseService.findCourseDetail(id);
        Message message = new Message();
        message.setStatus(OK);
        message.setMessage("조회 성공");
        message.setData(courseDetail);
        return new ResponseEntity<>(message, OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Message>  searchCourses(@RequestParam String keyword, @RequestParam String order){
        List<CoursePreviewDto> coursePreviewDtos = courseService.searchCourses(keyword, order);
        Message message = new Message();
        message.setStatus(OK);
        message.setMessage("조회 성공");
        message.setData(coursePreviewDtos);
        return new ResponseEntity<>(message, OK);
    }
}
