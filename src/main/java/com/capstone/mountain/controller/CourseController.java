package com.capstone.mountain.controller;

import com.capstone.mountain.domain.Mountain;
import com.capstone.mountain.dto.CourseDetailDto;
import com.capstone.mountain.dto.CoursePreviewDto;
import com.capstone.mountain.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/course/{course_id}")
    public CourseDetailDto findCourseDetail(@PathVariable("course_id") Long id){
        return courseService.findCourseDetail(id);
    }

    @GetMapping("/search")
    public List<CoursePreviewDto> searchCourses(@RequestParam String keyword, @RequestParam String order){
        return courseService.searchCourses(keyword, order);
    }
}
