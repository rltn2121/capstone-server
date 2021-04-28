package com.capstone.mountain.controller;

import com.capstone.mountain.domain.Mountain;
import com.capstone.mountain.dto.CoursePreviewDto;
import com.capstone.mountain.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/search")
    public List<CoursePreviewDto> searchCourses(@RequestParam String keyword, @RequestParam String order){
        System.out.println("controller에서 호출됨");
        return courseService.searchCourses(keyword, order);
    }
}
