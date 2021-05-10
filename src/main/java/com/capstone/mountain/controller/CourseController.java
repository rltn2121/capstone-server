package com.capstone.mountain.controller;

import com.capstone.mountain.Hello;
import com.capstone.mountain.Message;
import com.capstone.mountain.domain.Mountain;
import com.capstone.mountain.dto.CourseDetailDto;
import com.capstone.mountain.dto.CoursePreviewDto;
import com.capstone.mountain.exception.custom.NoResultException;
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

    @GetMapping("/course/{course_id}")
    public ResponseEntity<Message> findCourseDetail(@PathVariable("course_id") Long id){
        CourseDetailDto courseDetail = courseService.findCourseDetail(id);
        if(courseDetail.getId() == null){
            throw new NoResultException("조회 결과 없음.");
        }
        Message message = new Message();
        message.setStatus(OK);
        message.setMessage("조회 성공");
        message.setData(courseDetail);
        return new ResponseEntity<>(message, OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Message>  searchCourses(@RequestParam String keyword, @RequestParam String order){
        List<CoursePreviewDto> coursePreviewDtos = courseService.searchCourses(keyword, order);
        System.out.println("coursePreviewDtos.size() = " + coursePreviewDtos.size());
        if(coursePreviewDtos.size() == 0){
            throw new NoResultException("조회 결과 없음.");
        }
        Message message = new Message();
        message.setStatus(OK);
        message.setMessage("조회 성공");
        message.setData(coursePreviewDtos);
        for (CoursePreviewDto coursePreviewDto : coursePreviewDtos) {
            System.out.println("coursePreviewDto = " + coursePreviewDto);

        }
        return new ResponseEntity<>(message, OK);
    }
}
